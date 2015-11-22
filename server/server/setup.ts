
Meteor.startup(function () {
    if (Devices.find().count() === 0) {

        const device1 = new Device("iPhone van Tomas", DeviceType.iPhone, DeviceStatus.Unknown, "De iPhone van Tomas", new Date());
        Devices.insert(device1);

        const device2 = new Device("Nexus 6 van Tomas", DeviceType.Android, DeviceStatus.Unknown, "De Nexus 6 van Tomas", new Date());
        Devices.insert(device2);
    }
});