package cd.ekans.betterbats.toomuchbat;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import org.slf4j.Logger;

public class TooMuchBat {
    private static final Logger LOGGER = LogUtils.getLogger();

    private int batcount = 0;

    private int secs = 0;

    private boolean isCounting = false;

    public int getBatCount() {
        return batcount;
    }

    public void addBatCount() {
        this.batcount++;
    }

    public void addSecs() {
        this.secs++;
    }

    public void resetSecs() {
        this.secs = 0;
    }

    public int getSecs() {
        return secs;
    }

    public void startCounting() {
        this.isCounting = true;
    }

    public void stopCounting() {
        this.isCounting = false;
    }

    public boolean getIsCounting() {
        return isCounting;
    }

    public void resetBatCount() {
        this.batcount = 0;
    }

    public void copyFrom(TooMuchBat source) {
        this.batcount = source.batcount;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("batcount", batcount);
    }

    public void loadNBTData(CompoundTag nbt) {
        batcount = nbt.getInt("batcount");
    }
}
