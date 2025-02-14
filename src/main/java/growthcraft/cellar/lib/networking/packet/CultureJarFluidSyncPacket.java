package growthcraft.cellar.lib.networking.packet;

import growthcraft.cellar.block.entity.CultureJarBlockEntity;
import growthcraft.cellar.screen.CultureJarMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CultureJarFluidSyncPacket {
    private FluidStack fluidStack;
    private int tankID;

    private final BlockPos blockPos;

    public CultureJarFluidSyncPacket(int tankID, FluidStack fluidStack, BlockPos blockPos) {
        this.fluidStack = fluidStack;
        this.tankID = tankID;
        this.blockPos = blockPos;
    }

    public CultureJarFluidSyncPacket(FriendlyByteBuf byteBuf) {
        this.fluidStack = byteBuf.readFluidStack();
        this.blockPos = byteBuf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeFluidStack(this.fluidStack);
        byteBuf.writeBlockPos(this.blockPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork( () -> {
                    if(Minecraft.getInstance().level.getBlockEntity(this.blockPos) instanceof CultureJarBlockEntity blockEntity) {
                        blockEntity.setFluidStackInTank(this.tankID, this.fluidStack);

                        if(Minecraft.getInstance().player.containerMenu instanceof CultureJarMenu menu &&
                                menu.getBlockEntity().getBlockPos().equals(this.blockPos)) {
                            menu.setFluid(this.tankID, this.fluidStack);
                        }
                    }
                }
        );
        return true;
    }
}
