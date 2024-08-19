package pl.edu.pg.eti.ksg.po.project2.game;

public class Power {
    private boolean canActivate;
    private boolean isActive;
    private int duration;
    private int cooldown;

    public Power() {
        cooldown = 0;
        duration = 0;
        isActive = false;
        canActivate = true;
    }

    public void checkConditions() {
        if (cooldown > 0) cooldown--;
        if (duration > 0) duration--;
        if (duration == 0) deActivate();
        if (cooldown == 0) canActivate = true;
    }

    public void activate() {
        if (cooldown == 0) {
            isActive = true;
            canActivate = false;
            cooldown = 10;
            duration = 5;
        }
    }

    public void deActivate() {
        isActive = false;
    }

    public boolean getCanActivate() {
        return canActivate;
    }

    public void setCanActivate(boolean canActivate) {
        this.canActivate = canActivate;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
