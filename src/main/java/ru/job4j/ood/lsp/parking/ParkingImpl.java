package ru.job4j.ood.lsp.parking;

import java.util.Arrays;
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

    /**
     * Сначала определяется тип авто: легковой или грузовой.
     * Далее, в зависимости от типа авто выполняется поиск места для легковых или грузовых авто.
     */
    @Override
    public void takeSpace(Vehicle vehicle) {
        boolean isCar = vehicle.getSize() == 1;
        if (isCar) {
            takeSpaceForCar(vehicle);
        } else {
            takeSpaceForTruck(vehicle);
        }
    }

    @Override
    public void freeSpace(Vehicle vehicle) {
        boolean isCar = vehicle.getSize() == 1;
        if (isCar) {
            freeSpaceByCar(vehicle);
        } else {
            freeSpaceByTruck(vehicle);
        }
    }

    public Optional<Vehicle> isSpaceForCarOccupied(int numOfSpace) {
        return Optional.ofNullable(spacesForCar[numOfSpace].getVehicle());
    }

    public Optional<Vehicle> isSpaceForTruckOccupied(int numOfSpace) {
        return Optional.ofNullable(spacesForTruck[numOfSpace].getVehicle());
    }

    private void takeSpaceForCar(Vehicle vehicle) {
        boolean isSpaceTaking = false;
        for (var space : spacesForCar) {
            if (space.getVehicle() == null) {
                space.setVehicle(vehicle);
                isSpaceTaking = true;
                break;
            }
        }
        if (!isSpaceTaking) {
            throw new IllegalArgumentException("Нет свободных мест для авто " + vehicle);
        }
    }

    /**
     * Сначала выполняется поиск свободных мест в массиве мест для грузовых авто.
     * Если свободное место есть, оно занимается, и метод прекращает работу.
     * Если в массиве мест для грузовых авто нет свободных мест, поиск мест выполняется
     * в массиве мест для легковых авто.
     *
     * @param vehicle Грузовой автомобиль
     */
    private void takeSpaceForTruck(Vehicle vehicle) {
        for (var space : spacesForTruck) {
            if (space.getVehicle() == null) {
                space.setVehicle(vehicle);
                return;
            }
        }
        boolean isSpaceTaking = false;
        int[] indexes = new int[vehicle.getSize()];
        int idx = 0;
        for (int i = 0; i < spacesForCar.length; i++) {
            if (spacesForCar[i].getVehicle() == null) {
                indexes[idx++] = i;
                if (idx == indexes.length) {
                    isSpaceTaking = true;
                    break;
                }
            } else {
                idx = 0;
            }
        }
        if (!isSpaceTaking) {
            throw new IllegalArgumentException(String.format(
                    "Нет свободных мест для авто %s размером %s", vehicle, vehicle.getSize()
            ));
        }
        Arrays.stream(indexes)
                .forEach(i -> spacesForCar[i].setVehicle(vehicle));
    }

    private void freeSpaceByCar(Vehicle vehicle) {
        for (var space : spacesForCar) {
            if (space.getVehicle().equals(vehicle)) {
                space.setVehicle(null);
                break;
            }
        }
    }

    /**
     * Сначала выполняется поиск грузовика в массиве для грузовых автомобилей. Если авто
     * найдено, место освобождается и метод прекращает работу.
     * Если грузовик в массиве мест для грузовиков не найден, выполняется его поиск в массиве мест для легковых авто.
     * @param vehicle Грузовой автомобиль
     */
    private void freeSpaceByTruck(Vehicle vehicle) {
        for (var space : spacesForTruck) {
            if (space.getVehicle().equals(vehicle)) {
                space.setVehicle(null);
                return;
            }
        }
        int counter = 0;
        for (var space : spacesForCar) {
            if (space.getVehicle().equals(vehicle)) {
                space.setVehicle(null);
                counter++;
                if (counter == vehicle.getSize()) {
                    return;
                }
            }
        }
    }
}
