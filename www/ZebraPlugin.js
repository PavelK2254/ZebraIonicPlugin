var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'ZebraPlugin', 'coolMethod', [arg0]);
};

/*
arg0 - ip
arg1 - content
*/
exports.sendZplOverTcp = function (arg0,arg1, success, error) {
    exec(success, error, 'ZebraPlugin', 'sendZplOverTcp', [arg0,arg1]);
};

exports.sendCpclOverTcp = function (arg0,arg1, success, error) {
    exec(success, error, 'ZebraPlugin', 'sendCpclOverTcp', [arg0,arg1]);
};

exports.printConfigLabelUsingDnsName = function (arg0, success, error) {
    exec(success, error, 'ZebraPlugin', 'printConfigLabelUsingDnsName', [arg0]);
};

exports.printImageOverTcp = function(arg0,arg1,success,error){
  exec(success, error, 'ZebraPlugin', 'printImageOverTcp', [arg0]);
}
