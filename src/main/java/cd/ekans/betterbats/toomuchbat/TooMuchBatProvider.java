package cd.ekans.betterbats.toomuchbat;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TooMuchBatProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<TooMuchBat> TOO_MUCH_BAT = CapabilityManager.get(new CapabilityToken<TooMuchBat>() {
    });

    private TooMuchBat batcount = null;
    private final LazyOptional<TooMuchBat> optional = LazyOptional.of(this::createTooMuchBat);

    private TooMuchBat createTooMuchBat() {
        if (this.batcount == null) {
            this.batcount = new TooMuchBat();
        }
        return this.batcount;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        if (cap == TOO_MUCH_BAT) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createTooMuchBat().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createTooMuchBat().loadNBTData(nbt);
    }
}
