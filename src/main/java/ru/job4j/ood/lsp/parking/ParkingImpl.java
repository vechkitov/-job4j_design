package ru.job4j.ood.lsp.parking;

import java.util.Optional;

/**
 * Парковка для легковых и грузовых автомобилей.
 * Легковой автомобиль может занять только место, предназначенное для легкового автомобиля.
 * Грузовой автомобиль может разместиться на месте, предназначенном для грузового автомобиля, либо на
 * N парковочных мест для легковых автомобилей, стоящих рядом.
 * Легковым считается автомобиль, у которого размер равен 1. Грузовым - у которого размер > 1.
 */
public class ParkingImpl implements Parking {

    private final SpaceForCar[] spacesForCar;
    private final SpaceForTruck[] spacesForTruck;

    public ParkingImpl(SpaceForCar[] spacesForCar, SpaceForTruck[] spacesForTruck) {
        this.spacesForCar = spacesForCar;
        this.spacesForTruck = spacesForTruck;
    }

    @Override
    public void takeSpace(Vehicle vehicle) {

    }

    @Override
    public void freeSpace(Vehicle vehicle) {

    }

    public Optional<Vehicle> isSpaceForCarOccupied(int numOfSpace) {
        return Optional.empty();
    }

    public Optional<Vehicle> isSpaceForTruckOccupied(int numOfSpace) {
        return Optional.empty();
    }
}
