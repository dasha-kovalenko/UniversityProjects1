package media;

public class AbstractDisc {

    private String name;
    private double usedSpace;
    private boolean multisession;

    public String getName() {
        return this.name;
    }

    public boolean setName(String name) {
        if (this.multisession == true) {
            this.name = name;
            return true;
        }
        return false;
    }

    public double getUsedSpace() {
        return this.usedSpace;
    }
}
