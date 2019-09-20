package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

@Service
public class ShipServiceImplementation implements ShipService {
    private static Calendar calendarStart;
    private static Calendar calendarEnd;

    static {
        calendarStart = Calendar.getInstance();
        calendarStart.set(2800, Calendar.JANUARY, 1);

        calendarEnd = Calendar.getInstance();
        calendarEnd.set(3019, Calendar.DECEMBER, 31);
    }

    @Autowired
    ShipRepository shipRepository;

    @Override
    public Ship getById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Ship ship) {
        shipRepository.save(ship);
    }

    @Override
    public void delete(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public List<Ship> getAll() {
        return shipRepository.findAll();
    }

    @Override
    public Page<Ship> findAll(Specification<Ship> spec, Pageable pageable) {
        return shipRepository.findAll(spec, pageable);
    }

    @Override
    public List<Ship> findAll(Specification<Ship> spec) {
        return shipRepository.findAll(spec);
    }

    public boolean isValidShipCreated(Ship ship) {
        if (ship.getName() == null || ship.getPlanet() == null ||
                ship.getProdDate() == null || ship.getShipType() == null
                || ship.getSpeed() == null || ship.getCrewSize() == null) {
            return false;
        }

        if (ship.getName().length() > 50 || ship.getName().isEmpty() || ship.getPlanet().length() > 50 || ship.getPlanet().isEmpty() ||
                ship.getCrewSize() < 1 || ship.getCrewSize() > 9999 ||
                (double) Math.round(ship.getSpeed() * 100) / 100 < 0.01 || (double) Math.round(ship.getSpeed() * 100) / 100 > 0.99) {
            return false;
        }
        return !ship.getProdDate().before(calendarStart.getTime()) && !ship.getProdDate().after(calendarEnd.getTime());
    }

    public boolean isValidShipUpdated(Ship ship) {
        if (ship.getName() != null) {
            if (ship.getName().length() < 1 || ship.getName().length() > 50) {
                return false;
            }
        }
        if (ship.getPlanet() != null) {
            if (ship.getPlanet().length() < 1 || ship.getPlanet().length() > 50) {
                return false;
            }
        }
        if (ship.getProdDate() != null) {
            if (ship.getProdDate().before(calendarStart.getTime()) || ship.getProdDate().after(calendarEnd.getTime())) {
                return false;
            }
        }
        if (ship.getSpeed() != null) {
            if ((double) Math.round(ship.getSpeed() * 100) / 100 < 0.01 || (double) Math.round(ship.getSpeed() * 100) / 100 > 0.99) {
                return false;
            }
        }
        if (ship.getCrewSize() != null) {
            return ship.getCrewSize() >= 1 && ship.getCrewSize() <= 9999;
        }
        return true;
    }

    public Double countRating(Ship ship) {
        double k = ship.getUsed() ? 0.5 : 1;
        LocalDate localDate = LocalDate.of(3019, Month.JANUARY, 1);
        LocalDate produceDate = ship.getProdDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Math.round(80 * ship.getSpeed() * k / (localDate.getYear() - produceDate.getYear() + 1) * 100.0) / 100.0;
    }

    public static class ShipFilters {
        private String name;
        private String planet;
        private ShipType shipType;
        private Long after;
        private Long before;
        private Boolean isUsed;
        private Double minSpeed;
        private Double maxSpeed;
        private Integer minCrewSize;
        private Integer maxCrewSize;
        private Double minRating;
        private Double maxRating;

        public ShipFilters() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlanet() {
            return planet;
        }

        public void setPlanet(String planet) {
            this.planet = planet;
        }

        public ShipType getShipType() {
            return shipType;
        }

        public void setShipType(ShipType shipType) {
            this.shipType = shipType;
        }

        public Boolean getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(Boolean isUsed) {
            this.isUsed = isUsed;
        }

        public Long getAfter() {
            return after;
        }

        public void setAfter(Long after) {
            this.after = after;
        }

        public Long getBefore() {
            return before;
        }

        public void setBefore(Long before) {
            this.before = before;
        }

        public Double getMinSpeed() {
            return minSpeed;
        }

        public void setMinSpeed(Double minSpeed) {
            this.minSpeed = minSpeed;
        }

        public Double getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(Double maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public Integer getMinCrewSize() {
            return minCrewSize;
        }

        public void setMinCrewSize(Integer minCrewSize) {
            this.minCrewSize = minCrewSize;
        }

        public Integer getMaxCrewSize() {
            return maxCrewSize;
        }

        public void setMaxCrewSize(Integer maxCrewSize) {
            this.maxCrewSize = maxCrewSize;
        }

        public Double getMinRating() {
            return minRating;
        }

        public void setMinRating(Double minRating) {
            this.minRating = minRating;
        }

        public Double getMaxRating() {
            return maxRating;
        }

        public void setMaxRating(Double maxRating) {
            this.maxRating = maxRating;
        }
    }
}