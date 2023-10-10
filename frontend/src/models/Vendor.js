export class Vendor {
    constructor(id, name, address, phone, email, contact, standardDeliveryTime, notes) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.contact = contact;
        this.standardDeliveryTime = standardDeliveryTime;
        this.notes = notes;
    }

    static createDummyVendor(id){
        return new Vendor(id, "Dummy Vendor", "Dummy Address", "Dummy Phone",
            "Dummy Email", "Dummy Contact", "Dummy Delivery Time", "Dummy Notes");
    }
}