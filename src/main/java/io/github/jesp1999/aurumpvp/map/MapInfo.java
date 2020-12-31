package io.github.jesp1999.aurumpvp.map;

import java.util.List;
import java.util.Set;

/**
 * Class representing the information associated with a kit pvp map
 * @author Julian Espada
 *
 */
public class MapInfo {
    private float x1, y1, z1, x2, y2, z2;
    private float spawnX, spawnY, spawnZ;
    private String mapName;
    
    /**
     * Constructor for a MapInfo given a name, 2 points representing the bounding box of the map, and 1 point representing the spawn coordinates of a player entering.
     * @param mapName String identifier for the map
     * @param x1 x-coordinate for one corner of the map bounding box
     * @param y1 y-coordinate for one corner of the map bounding box
     * @param z1 z-coordinate for one corner of the map bounding box
     * @param x2 x-coordinate for the other corner of the map bounding box
     * @param y2 y-coordinate for the other corner of the map bounding box
     * @param z2 z-coordinate for the other corner of the map bounding box
     * @param spawnX x-coordinate for the spawn point of a player on this map
     * @param spawnY y-coordinate for the spawn point of a player on this map
     * @param spawnZ z-coordinate for the spawn point of a player on this map
     */
    public MapInfo(String mapName, float x1, float y1, float z1, float x2, float y2, float z2, float spawnX, float spawnY, float spawnZ) {
       this.mapName = mapName;
       this.x1 = x1;
       this.y1 = y1;
       this.z1 = z1;
       this.x2 = x2;
       this.y2 = y2;
       this.z2 = z2;
       this.spawnX = spawnX;
       this.spawnY = spawnY;
       this.spawnZ = spawnZ;
    }
    
    /**
     * Retrieves the String representation of the name of the map
     * @return the name of this MapInfo as a String
     */
    public String getMapName() {
        return this.mapName;
    }

    /**
     * Retrieves the coordinates for the spawn-point on this map as a List<Float>
     * @return the coordinates for the spawn-point on this map as a List<Float>
     */
    public List<Float> getSpawnCoordinates() {
        return List.of(this.spawnX, this.spawnY, this.spawnZ);
    }

    /**
     * Retrieves the coordinates of the bounding box of this map as a Set<List<Float>>
     * @return set of the coordinates which define the rectangular boundary of this map as a Set<List<FLoat>>
     */
    public Set<List<Float>> getMapOrigins() {
        return Set.of(List.of(this.x1, this.y1, this.z1), List.of(this.x2, this.y2, this.z2));
    }
}
