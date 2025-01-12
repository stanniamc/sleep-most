package me.mrgeneralq.sleepmost.commands.subcommands;

import me.mrgeneralq.sleepmost.enums.MessageKey;
import me.mrgeneralq.sleepmost.interfaces.*;
import me.mrgeneralq.sleepmost.templates.MessageTemplate;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SleepSubCommand implements ISubCommand {

    private final ISleepService sleepService;
    private final IFlagsRepository flagsRepository;
    private final IMessageService messageService;
    private final IWorldPropertyService worldPropertyService;

    public SleepSubCommand(ISleepService sleepService, IFlagsRepository flagsRepository, IMessageService messageService, ICooldownService cooldownService, IBossBarService bossBarService, IWorldPropertyService worldPropertyService) {
        this.sleepService = sleepService;
        this.flagsRepository = flagsRepository;
        this.messageService = messageService;
        this.worldPropertyService = worldPropertyService;
    }


    @Override
    public boolean executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {


        Player player = (Player) sender;
        World world = player.getWorld();

        if(!this.flagsRepository.getSleepCmdFlag().getValueAt(world)){
            this.messageService.sendMessage(player, this.messageService.getMessagePrefixed(MessageKey.SLEEP_CMD_DISABLED).build());
            return true;
        }

        //check if sleep is allowed
        if(this.flagsRepository.getPreventSleepFlag().getValueAt(world)) {
            String sleepPreventedConfigMessage = messageService.getMessagePrefixed(MessageKey.SLEEP_PREVENTED)
                    .setWorld(world)
                    .setPlayer(player)
                    .build();

            this.messageService.sendMessage(player, messageService.getMessagePrefixed(sleepPreventedConfigMessage)
                    .setPlayer(player)
                    .setWorld(world)
                    .build());
            return true;
        }

        //check if reset is required
        if (!this.sleepService.resetRequired(world)) {
            this.messageService.sendMessage(player, messageService.getMessagePrefixed(MessageKey.CANNOT_SLEEP_NOW)
                    .setWorld(world)
                    .build());
            return true;
        }


        if(this.worldPropertyService.getWorldProperties(world).isInsomniaEnabled()){
            String insomniaMessage = this.messageService.getMessagePrefixed(MessageKey.INSOMNIA_NOT_SLEEPY)
                    .setWorld(world)
                    .build();
            this.messageService.sendMessage(player, insomniaMessage);
            return true;
        }


        boolean updatedSleepStatus = !this.sleepService.isPlayerAsleep(player);

        //TODO check this what the original getStatusTemplate is
      //  this.messageService.sendMessage(player, this.messageService.getMessage(getStatusTemplate(updatedSleepStatus)).build());

        this.sleepService.setSleeping(player, updatedSleepStatus);
        return true;
    }
    private MessageTemplate getStatusTemplate(boolean sleepingStatus){
        return sleepingStatus ? MessageTemplate.SLEEP_SUCCESS : MessageTemplate.NO_LONGER_SLEEPING;
    }
}

