package seedu.address.model.route;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the route-list level
 * Duplicates are not allowed (by .isSameRoute comparison)
 */
public class RouteList implements ReadOnlyRouteList {

    private final UniqueRouteList routes;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        routes = new UniqueRouteList();
    }

    public RouteList() {}

    /**
     * Creates an RouteList using the Routes in the {@code toBeCopied}
     */
    public RouteList(ReadOnlyRouteList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the route list with {@code routes}.
     * {@code routes} must not contain duplicate routes.
     */
    public void setRoutes(List<Route> routes) {
        this.routes.setRoutes(routes);
    }

    /**
     * Resets the existing data of this {@code RouteList} with {@code newData}.
     */
    public void resetData(ReadOnlyRouteList newData) {
        requireNonNull(newData);

        setRoutes(newData.getRouteList());
    }

    //// route-level operations

    /**
     * Returns true if a route with the same identity as {@code route} exists in the route book.
     */
    public boolean hasRoute(Route route) {
        requireNonNull(route);
        return routes.contains(route);
    }

    /**
     * Adds a route to the route book.
     * The route must not already exist in the route book.
     */
    public void addRoute(Route route) {
        if (route.getId() == null) {
            route.assignId();
        }
        routes.add(route);
    }

    /**
     * Replaces the given route {@code target} in the list with {@code editedRoute}.
     * {@code target} must exist in the route book.
     * The route identity of {@code editedRoute} must not be the same as another existing route in the route book.
     */
    public void updateRoute(Route target, Route editedRoute) {
        requireNonNull(editedRoute);

        routes.setRoute(target, editedRoute);
    }

    /**
     * Removes {@code key} from this {@code RouteList}.
     * {@code key} must exist in the route book.
     */
    public void removeRoute(Route key) {
        routes.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return routes.asUnmodifiableObservableList().size() + " routes";
    }

    @Override
    public ObservableList<Route> getRouteList() {
        return routes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RouteList // instanceof handles nulls
                && routes.equals(((RouteList) other).routes));
    }

    @Override
    public int hashCode() {
        return routes.hashCode();
    }
}
