package me.qintinator.sleepmost.flags;
import me.qintinator.sleepmost.enums.FlagType;
import me.qintinator.sleepmost.interfaces.ISleepFlag;
import org.bukkit.World;

public class MobNoTargetFlag implements ISleepFlag<Boolean> {
    @Override
    public String getFlagName() {
        return "mob-no-target";
    }

    @Override
    public String getFlagUsage() {
        return "/sleepmost setflag mob-no-target <true|false>";
    }

    @Override
    public boolean isValidValue(String value) {
        return value.equals("true")||value.equals("false");
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.Boolean;
    }

    @Override
    public Boolean getValue(World world) {
        return null;
    }

    @Override
    public void setValue(World world, Boolean value) {

    }

}
