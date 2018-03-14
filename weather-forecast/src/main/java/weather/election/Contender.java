package weather.election;

import java.util.Objects;

public final class Contender {
    private final String serviceId;
    private final String service;
    private final String address;
    private final int port;

    public Contender(String serviceId, String service, String address, int port) {
        this.serviceId = Objects.requireNonNull(serviceId);
        this.service = Objects.requireNonNull(service);
        this.address = Objects.requireNonNull(address);
        this.port = Objects.requireNonNull(port);
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getService() {
        return service;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contender contender = (Contender) o;
        return port == contender.port &&
                Objects.equals(serviceId, contender.serviceId) &&
                Objects.equals(service, contender.service) &&
                Objects.equals(address, contender.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, service, address, port);
    }
}
