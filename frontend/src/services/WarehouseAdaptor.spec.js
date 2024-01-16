import WarehousesAdaptor from "@/services/WarehousesAdaptor";
import { Warehouse } from "@/models/Warehouse";
import '@jest/globals';

describe('WarehousesAdaptor', () => {
    let warehousesAdaptor;
    let resourceUrl = 'http://localhost:8087/api/warehouses';

    beforeEach(() => {
        warehousesAdaptor = new WarehousesAdaptor(resourceUrl);
        jest.spyOn(warehousesAdaptor, 'fetchJson').mockResolvedValue({});
    });

    test('asyncFindAll fetches all warehouses successfully', async () => {
        const mockWarehouses = [{ id: 1, name: 'Warehouse 1' }, { id: 2, name: 'Warehouse 2' }];
        jest.spyOn(warehousesAdaptor, 'fetchJson').mockResolvedValue(mockWarehouses);

        const result = await warehousesAdaptor.asyncFindAll();

        expect(result).toEqual(mockWarehouses.map(warehouse => Warehouse.fromJson(warehouse)));
    });

    test('asyncFindById fetches a warehouse by ID successfully', async () => {
        const warehouseId = '1';
        const mockWarehouse = { id: 1, name: 'Warehouse 1' };
        jest.spyOn(warehousesAdaptor, 'fetchJson').mockResolvedValue(mockWarehouse);

        const result = await warehousesAdaptor.asyncFindById(warehouseId);

        expect(result).toEqual(Warehouse.fromJson(mockWarehouse));
    });

    test('asyncAdd adds a warehouse successfully', async () => {
        const newWarehouse = { name: 'New Warehouse' };
        const mockAddedWarehouse = { id: 3, name: 'New Warehouse' };
        jest.spyOn(warehousesAdaptor, 'fetchJson').mockResolvedValue(mockAddedWarehouse);

        const result = await warehousesAdaptor.asyncAdd(newWarehouse);

        expect(result).toEqual(Warehouse.fromJson(mockAddedWarehouse));
    });

    test('asyncUpdate updates a warehouse successfully', async () => {
        const warehouseId = '1';
        const updatedWarehouseData = { name: 'Updated Warehouse' };
        const mockUpdatedWarehouse = { id: 1, name: 'Updated Warehouse' };
        jest.spyOn(warehousesAdaptor, 'fetchJson').mockResolvedValue(mockUpdatedWarehouse);

        const result = await warehousesAdaptor.asyncUpdate(warehouseId, updatedWarehouseData);

        expect(result).toEqual(Warehouse.fromJson(mockUpdatedWarehouse));
    });

    test('asyncDeleteById deletes a warehouse by ID successfully', async () => {
        const warehouseId = '1';
        const mockDeletedWarehouse = { id: 1, name: 'Deleted Warehouse' };
        jest.spyOn(warehousesAdaptor, 'fetchJson').mockResolvedValue(mockDeletedWarehouse);

        const result = await warehousesAdaptor.asyncDeleteById(warehouseId);

        expect(result).toEqual(Warehouse.fromJson(mockDeletedWarehouse));
    });
});