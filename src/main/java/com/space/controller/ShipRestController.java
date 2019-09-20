package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipServiceImplementation;
import com.space.service.ShipSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShipRestController {

    @Autowired
    private ShipServiceImplementation shipService;

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = this.shipService.getById(id);

        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @RequestMapping(value = "/ships", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Ship> addShip(@RequestBody @Validated Ship ship) {
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        if (!shipService.isValidShipCreated(ship)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ship.setRating(shipService.countRating(ship));
        this.shipService.save(ship);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Ship> updateShip(@PathVariable("id") Long id, @RequestBody @Validated Ship ship) {
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        if (id <= 0 || !shipService.isValidShipUpdated(ship)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship foundShip = this.shipService.getById(id);
        if (foundShip == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (ship.getName() != null) {
            foundShip.setName(ship.getName());
        }
        if (ship.getShipType() != null) {
            foundShip.setShipType(ship.getShipType());
        }
        if (ship.getCrewSize() != null) {
            foundShip.setCrewSize(ship.getCrewSize());
        }
        if (ship.getPlanet() != null) {
            foundShip.setPlanet(ship.getPlanet());
        }
        if (ship.getProdDate() != null) {
            foundShip.setProdDate(ship.getProdDate());
        }
        if (ship.getSpeed() != null) {
            foundShip.setSpeed(ship.getSpeed());
        }
        if (ship.getUsed() != null) {
            foundShip.setUsed(ship.getUsed());
        }
        foundShip.setRating(shipService.countRating(foundShip));
        this.shipService.save(foundShip);

        return new ResponseEntity<>(foundShip, HttpStatus.OK);
    }

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Ship> deleteShip(@PathVariable("id") Long id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = this.shipService.getById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.shipService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/ships", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Ship>> getAllShips(ShipServiceImplementation.ShipFilters shipFilters,
                                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                                                  @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
                                                  @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, order.getFieldName());
        Page<Ship> ships = this.shipService.findAll(new ShipSpecification(shipFilters), pageable);
        return new ResponseEntity<>(ships.getContent(), HttpStatus.OK);
    }

    @RequestMapping(value = "/ships/count", method = RequestMethod.GET, produces = "application/json")
    public int getShipsCount(ShipServiceImplementation.ShipFilters shipFilters) {
        List<Ship> ships = this.shipService.findAll(new ShipSpecification(shipFilters));
        return ships.size();
    }
}