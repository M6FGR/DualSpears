package diego.efds.gameasset;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;

public class DualSpearsCollider {
    public static final Collider SPEAR = new MultiOBBCollider(3, 0.6, 0.6, 1.0, 0.0, 0.0, -1.0);
    public static final Collider SLASH = new MultiOBBCollider(3, 1.0, 1.0, 1.0, 0.0, 1.5, -2.0);
    public static final Collider SLAM = new MultiOBBCollider(6, 2.0, 1.5, 2.0, 0.0, 0.5, -1.0);
    public DualSpearsCollider() {
    }
}