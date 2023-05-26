package dartcraftReloaded.capablilities.Modifiable;

import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.concurrent.Callable;

public class ModifiableFactory implements Callable<IModifiable> {
    @Override
    public IModifiable call() throws Exception {
        return new IModifiable() {

            private HashMap<Integer, Integer> modifiers = new HashMap<>();

            @Override
            public void setModifier(int id, int level) {
                modifiers.put(id, level);
            }

            @Override
            public void setModifiers(HashMap<Integer, Integer> modifiers) {
                this.modifiers = modifiers;
            }

            @Override
            public HashMap<Integer, Integer> getModifiers() {
                return modifiers;
            }

            @Override
            public boolean hasModifier(int id) {
                if (modifiers.containsKey(id)) {
                    return modifiers.get(id) > 0;
                }
                return false;
            }

            @Override
            public boolean hasModifier(Modifier m) {
                return hasModifier(m.getId());
            }

            @Override
            public int getLevel(int id) {
                if (modifiers.containsKey(id)) {
                    return modifiers.get(id);
                }
                return 0;
            }

            @Override
            public int getLevel(Modifier m) {
                return getLevel(m.getId());
            }


            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound nbt = new NBTTagCompound();
                int[] keys = new int[modifiers.size()];
                int counter = 0;
                for (int i : modifiers.keySet()) {
                    keys[counter] = i;
                    counter++;
                    nbt.setInteger(String.valueOf(i), modifiers.get(i));
                }

                nbt.setIntArray("keys", keys);
                return nbt;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                modifiers.clear();
                int[] keys = nbt.getIntArray("keys");
                for (int i : keys) {
                    modifiers.put(i, nbt.getInteger(String.valueOf(i)));
                }
            }
        };
    }
}
