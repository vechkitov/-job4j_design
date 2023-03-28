package ru.job4j.ood.lsp.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ParkingImplTest {

    static Vehicle car1 = new Vehicle(1);
    static Vehicle car2 = new Vehicle(1);
    static Vehicle truck1 = new Vehicle(2);
    static Vehicle truck2 = new Vehicle(2);
    SpaceForCar[] spacesCar;
    SpaceForTruck[] spacesTruck;

    @BeforeEach
    public void init() {
        var spaceCar0Taken = new SpaceForCar();
        spaceCar0Taken.setVehicle(car1);
        var spaceCar1 = new SpaceForCar();
        var spaceCar2Taken = new SpaceForCar();
        spaceCar2Taken.setVehicle(car2);
        var spaceCar3 = new SpaceForCar();
        var spaceCar4 = new SpaceForCar();
        spacesCar = new SpaceForCar[]{spaceCar0Taken, spaceCar1, spaceCar2Taken, spaceCar3, spaceCar4};

        var spaceTruck0Taken = new SpaceForTruck();
        spaceTruck0Taken.setVehicle(truck1);
        var spaceTruck1 = new SpaceForTruck();
        var spaceTruck2Taken = new SpaceForTruck();
        spaceTruck2Taken.setVehicle(truck2);
        var spaceTruck3 = new SpaceForTruck();
        spacesTruck = new SpaceForTruck[]{spaceTruck0Taken, spaceTruck1, spaceTruck2Taken, spaceTruck3};
    }

    @Test
    void whenCarIsParkedAndThereAreFreeSpaces() {
        var parking = new ParkingImpl(spacesCar, new SpaceForTruck[]{});
        var car = new Vehicle(1);
        parking.takeSpace(car);
        assertThat(parking.isSpaceForCarOccupied(1))
                .contains(car);
        assertThat(parking.isSpaceForCarOccupied(3))
                .isNotPresent();
    }

    @Test
    void whenCarIsParkedAndThereAreNoFreeSpaces() {
        var space0 = new SpaceForCar();
        space0.setVehicle(car1);
        var space1 = new SpaceForCar();
        space1.setVehicle(car2);
        SpaceForCar[] spacesForCar = {space0, space1};
        var parking = new ParkingImpl(spacesForCar, new SpaceForTruck[]{});
        assertThatIllegalArgumentException().isThrownBy(
                () -> parking.takeSpace(new Vehicle(1))
        );
    }

    @Test
    void whenCarFreesUpSpace() {
        var parking = new ParkingImpl(spacesCar, new SpaceForTruck[]{});
        assertThat(parking.isSpaceForCarOccupied(2))
                .contains(car2);
        parking.freeSpace(car2);
        assertThat(parking.isSpaceForCarOccupied(2))
                .isNotPresent();
    }

    @Test
    void whenTruckIsParkedAndThereAreFreeSpacesForTrucks() {
        var parking = new ParkingImpl(new SpaceForCar[]{}, spacesTruck);
        var truck = new Vehicle(2);
        parking.takeSpace(truck);
        assertThat(parking.isSpaceForTruckOccupied(1))
                .contains(truck);
        assertThat(parking.isSpaceForTruckOccupied(3))
                .isNotPresent();
    }

    @Test
    void whenTruckIsParkedAndThereAreNoFreeSpacesForTrucksButThereAreFreeSpacesForCars() {
        var spaceTruck = new SpaceForTruck();
        spaceTruck.setVehicle(truck1);
        SpaceForTruck[] spacesTruck = {spaceTruck};
        var parking = new ParkingImpl(spacesCar, spacesTruck);
        var truck = new Vehicle(2);
        parking.takeSpace(truck);
        assertThat(parking.isSpaceForCarOccupied(1))
                .isNotPresent();
        assertThat(parking.isSpaceForCarOccupied(3))
                .contains(truck);
        assertThat(parking.isSpaceForCarOccupied(4))
                .contains(truck);
    }

    @Test
    void whenTruckIsParkedAndThereAreNoAnyFreeSpaces() {
        var spaceCar0 = new SpaceForCar();
        spaceCar0.setVehicle(car1);
        var spaceCar1 = new SpaceForCar();
        SpaceForCar[] spacesCar = {spaceCar0, spaceCar1};
        var spaceTruck = new SpaceForTruck();
        spaceTruck.setVehicle(truck1);
        SpaceForTruck[] spacesTruck = {spaceTruck};
        var parking = new ParkingImpl(spacesCar, spacesTruck);
        assertThatIllegalArgumentException().isThrownBy(
                () -> parking.takeSpace(new Vehicle(2))
        );
    }

    @Test
    void whenCarFreesUpSpaceForTrucks() {
        var parking = new ParkingImpl(spacesCar, spacesTruck);
        assertThat(parking.isSpaceForTruckOccupied(2))
                .contains(truck2);
        parking.freeSpace(truck2);
        assertThat(parking.isSpaceForTruckOccupied(2))
                .isNotPresent();
    }

    @Test
    void whenCarFreesUpSpacesForCars() {
        var spaceCar0 = new SpaceForCar();
        spaceCar0.setVehicle(truck1);
        var spaceCar1 = new SpaceForCar();
        spaceCar1.setVehicle(truck1);
        SpaceForCar[] spacesCar = {spaceCar0, spaceCar1};
        var parking = new ParkingImpl(spacesCar, new SpaceForTruck[]{});
        parking.freeSpace(truck1);
        assertThat(parking.isSpaceForCarOccupied(0))
                .isNotPresent();
        assertThat(parking.isSpaceForCarOccupied(1))
                .isNotPresent();
    }
}
