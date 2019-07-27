var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'ZebraPlugin', 'coolMethod', [arg0]);
};

exports.sendZplOverTcp = function (arg0, success, error) {
    exec(success, error, 'ZebraPlugin', 'sendZplOverTcp', [arg0]);
};

exports.sendCpclOverTcp = function (arg0, success, error) {
    exec(success, error, 'ZebraPlugin', 'sendCpclOverTcp', [arg0]);
};

exports.printConfigLabelUsingDnsName = function (arg0, success, error) {
    exec(success, error, 'ZebraPlugin', 'printConfigLabelUsingDnsName', [arg0]);
};
