package cd.ekans.betterbats.event;

import cd.ekans.betterbats.BetterBats;
import cd.ekans.betterbats.config.ServerConfig;
import cd.ekans.betterbats.item.ModItems;
import cd.ekans.betterbats.toomuchbat.TooMuchBat;
import cd.ekans.betterbats.toomuchbat.TooMuchBatProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = BetterBats.MODID)
public class ModEvents {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(TooMuchBatProvider.TOO_MUCH_BAT).isPresent()) {
                event.addCapability(new ResourceLocation(BetterBats.MODID, "properties"), new TooMuchBatProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(TooMuchBatProvider.TOO_MUCH_BAT).ifPresent(oldStore -> {
                event.getOriginal().getCapability(TooMuchBatProvider.TOO_MUCH_BAT).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(TooMuchBat.class);
    }

    @SubscribeEvent
    public static void tooMuchBat(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player &&
                event.getItem().is(ModItems.COOKED_BAT.get()) &&
                !player.level.isClientSide) {
            player.getCapability(TooMuchBatProvider.TOO_MUCH_BAT).ifPresent(tooMuchBat -> {
                if (!tooMuchBat.getIsCounting()) {
                    tooMuchBat.startCounting();
                }
                if (tooMuchBat.getBatCount() == ServerConfig.MAX_BAT_COUNT.get() - 1) {
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER, 20000000, 0));
                } else {
                    tooMuchBat.addBatCount();
                }
            });
        } else if (event.getEntity() instanceof Player player &&
                event.getItem().isEdible() &&
                !event.getItem().is(ModItems.COOKED_BAT.get()) &&
                !event.getItem().is(Items.ROTTEN_FLESH) &&
                !event.getItem().is(Items.SPIDER_EYE) &&
                !event.getItem().is(Items.PUFFERFISH) &&
                !player.level.isClientSide) {
                    player.getCapability(TooMuchBatProvider.TOO_MUCH_BAT).ifPresent(tooMuchBat -> {
                        tooMuchBat.resetBatCount();
                        tooMuchBat.stopCounting();
                        player.removeEffect(MobEffects.WITHER);
            });
        }
    }

    @SubscribeEvent
    public static void ticks(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer()) {
            event.player.getCapability(TooMuchBatProvider.TOO_MUCH_BAT).ifPresent(tooMuchBat -> {
                if (tooMuchBat.getSecs() >= (ServerConfig.COUNT_COOLDOWN.get() * 20)) {
                    tooMuchBat.resetSecs();
                    tooMuchBat.resetBatCount();
                }
                if (tooMuchBat.getIsCounting()) {
                    tooMuchBat.addSecs();
                }
            });
        }
    }
}