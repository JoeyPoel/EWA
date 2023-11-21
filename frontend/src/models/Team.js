export class Team {
    constructor(id, name, warehouseId) {
        this._id = id;
        this._name = name;
        this._warehouseId = warehouseId;
    }

    get id() {
        return this._id;
    }

    set id(value) {
        this._id = value;
    }

    get name() {
        return this._name;
    }

    set name(value) {
        this._name = value;
    }

    get warehouseId() {
        return this._warehouseId;
    }

    set warehouseId(value) {
        this._warehouseId = value;
    }
}
