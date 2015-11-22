/// <reference path="../typings/tsd.d.ts" />

enum DeviceType {
    iPhone, Android
}

enum DeviceStatus {
    Unknown, Online, Offline
}

class Device {
    _id: string;
    name: string;
    type: DeviceType;
    status: DeviceStatus;
    description: string;
    added: Date;

    constructor(name:string, type:DeviceType, status:DeviceStatus, description:string, added:Date) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.description = description;
        this.added = added;
    }
}

declare var Devices: Mongo.Collection<Device>;
Devices = new Mongo.Collection<Device>('devices');

this.DeviceType = DeviceType;
this.DeviceStatus = DeviceStatus;
this.Devices = Devices;
this.Device = Device;