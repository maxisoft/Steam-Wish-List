package ga.maxisoft.steam.wishlist;

public class SteamGame implements Comparable<SteamGame> {
    private final long id;
    private final String name;


    public SteamGame(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SteamGame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SteamGame)) return false;

        SteamGame steamGame = (SteamGame) o;

        if (id != steamGame.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(SteamGame o) {
        return Long.compare(id, o.id);
    }
}
