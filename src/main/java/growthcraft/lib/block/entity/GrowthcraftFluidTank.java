package growthcraft.lib.block.entity;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.Collections;
import java.util.List;

public class GrowthcraftFluidTank extends FluidTank {

    private List<Fluid> fluidsList;
    private boolean isOutputOnly = false;
    private boolean allowAnyFluid = false;

    public GrowthcraftFluidTank(int capacity) {
        this(capacity, Collections.singletonList(Fluids.EMPTY), false);
    }

    public GrowthcraftFluidTank(int capacity, boolean isOutputOnly) {
        this(capacity, Collections.singletonList(Fluids.EMPTY), isOutputOnly);
    }

    public GrowthcraftFluidTank(int capacity, List<Fluid> validFluids, boolean isOutputOnly) {
        super(capacity);
        this.fluidsList = validFluids;
        this.isOutputOnly = isOutputOnly;
    }

    public void setFluidFilter(List<Fluid> validFluids) {
        this.fluidsList = fluidsList;
    }

    public void addFluidFilter(Fluid fluid) {
        this.fluidsList.add(fluid);
    }

    /**
     * Setter for determining whether or not to all all fluids into the tank. Defaults to false.
     * @param allowAnyFluid Boolean indicating whether or not to allow any fluid into the tank object.
     */
    public void allowAnyFluid(boolean allowAnyFluid) {
        this.allowAnyFluid = allowAnyFluid;
    }

    @Override
    protected void onContentsChanged() {
        super.onContentsChanged();
    }

    /**
     * Determine if an input fluid ia valid for this fluid tank.
     * @param stack Fluidstack holding the Fluid to be queried.
     * @return Boolean indicating if the fluid is valid for this tank.
     */
    @Override
    public boolean isFluidValid(FluidStack stack) {
        if(isOutputOnly) return false;
        if(allowAnyFluid) return true;
        return (fluidsList.get(0) == Fluids.EMPTY || fluidsList.contains(stack.getFluid()));
    }

}
