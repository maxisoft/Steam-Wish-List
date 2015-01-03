package ga.maxisoft.steam.wishlist;

import com.github.kevinsawicki.http.HttpRequest;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class WhishList implements Set<SteamGame> {

    private final String id;

    private final Set<SteamGame> wrapped;

    private boolean readOnly;

    public WhishList(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id must be not null");
        }
        this.id = id;
        wrapped = new LinkedHashSet<SteamGame>();
        readOnly = true;
        refresh();
    }


    public void refresh() {
        Jerry jerry = Jerry.jerry(HttpRequest.get("http://steamcommunity.com/id/" + id + "/wishlist/").body());
        jerry.$(".wishlistRow").each(new JerryFunction() {
            @Override
            public boolean onNode(Jerry $this, int index) {
                long gameId = Long.parseLong($this.attr("id").replaceFirst("game_", ""));
                String gameName = $this.$("h4").text().trim();
                SteamGame steamGame = new SteamGame(gameId, gameName);
                wrapped.add(steamGame);
                return true;
            }
        });
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public int size() {
        return wrapped.size();
    }

    @Override
    public boolean isEmpty() {
        return wrapped.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return wrapped.contains(o);
    }

    @Override
    public Iterator<SteamGame> iterator() {
        return wrapped.iterator();
    }

    @Override
    public Object[] toArray() {
        return wrapped.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return wrapped.toArray(a);
    }

    @Override
    public boolean add(SteamGame game) {
        if (readOnly) {
            throw new UnsupportedOperationException();
        }
        return wrapped.add(game);
    }

    @Override
    public boolean remove(Object o) {
        if (readOnly) {
            throw new UnsupportedOperationException();
        }
        return wrapped.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends SteamGame> c) {
        if (readOnly) {
            throw new UnsupportedOperationException();
        }
        return wrapped.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return wrapped.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (readOnly) {
            throw new UnsupportedOperationException();
        }
        return wrapped.removeAll(c);
    }

    @Override
    public void clear() {
        if (readOnly) {
            throw new UnsupportedOperationException();
        }
        wrapped.clear();
    }

    @Override
    public String toString() {
        return wrapped.toString();
    }
}
