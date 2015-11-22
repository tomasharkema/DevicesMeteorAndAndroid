const currentDeviceId = "currentDeviceId";
Session.setDefault(currentDeviceId, "");

Template['deviceList'].helpers({
    "devices": function() {
        return Devices.find({})
    }
});

Template['deviceList'].events({
   "click #deviceList li": function(event) {
       event.preventDefault();

       const device: Device = this;
       Session.set(currentDeviceId, device._id);
       $.mobile.changePage("#device-detail");
       console.log(device);
   }
});

Template['deviceList'].onRendered(function() {
    this.autorun(function() {
        setTimeout(function() {
            $("#deviceList").listview("refresh");
        }, 100);
    });
});

Template['add'].helpers({
    "types": function() {
        var keys = Object.keys(DeviceType);

        var types = [];
        var i = 0;
        for (var key in keys) {
            if (i < keys.length/2) {
                types[i] = { name: DeviceType[key], key: key };
            }

            i++;
        }
        return types
    }
});

Template["add"].events({
    "click #add-submit": function(event) {
        event.preventDefault();

        const name = $("#name").val();
        const description = $("#desc").val();
        const type = parseInt($("#type").val());

        console.log(name, type);

        const device = new Device(name, type, DeviceStatus.Unknown, description, new Date());
        Devices.insert(device);
        $.mobile.changePage("#device-list");
    }

});

Template["deviceDetail"].helpers({
    device: function() {
        return Devices.findOne({_id: Session.get(currentDeviceId)});
    },
    type: function() {
        return DeviceType[Devices.findOne({_id: Session.get(currentDeviceId)}).type];
    }
});

Template["deviceDetail"].events({
    "click #remove": function(event) {
        event.preventDefault();
        Devices.remove(Session.get(currentDeviceId));
        $.mobile.changePage("#device-list");
    }
});