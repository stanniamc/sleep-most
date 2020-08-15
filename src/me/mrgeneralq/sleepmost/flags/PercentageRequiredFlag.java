package me.mrgeneralq.sleepmost.flags;

import me.mrgeneralq.sleepmost.enums.FlagType;
import me.mrgeneralq.sleepmost.interfaces.IConfigRepository;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlag;
import me.mrgeneralq.sleepmost.interfaces.ISleepFlagService;
import org.bukkit.World;

import me.mrgeneralq.sleepmost.statics.Bootstrapper;

public class PercentageRequiredFlag implements ISleepFlag<Double> {


    private final IConfigRepository configRepository;
    private final Bootstrapper bootstrapper;
    private final ISleepFlagService sleepFlagService;

    public PercentageRequiredFlag(){
    	this.configRepository = Bootstrapper.getBootstrapper().getConfigRepository();
    	this.bootstrapper = Bootstrapper.getBootstrapper();
        this.sleepFlagService = this.bootstrapper.getSleepFlagService();
    }

    @Override
    public String getFlagName() {
       return "percentage-required";
    }

    @Override
    public String getFlagUsage() {
        return "Use /sleepmost setflag percentage-required <0.1 - 1>";
    }

    @Override
    public boolean isValidValue(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public FlagType getFlagType() {
        return FlagType.DOUBLE;
    }

    @Override
    public Double getValue(World world) {
        if(sleepFlagService.getFlag(world, this.getFlagName()) == null)
            return null;
        return (Double) sleepFlagService.getFlag(world, this.getFlagName());
    }

    @Override
    public void setValue(World world, Double value) {

    }
}
