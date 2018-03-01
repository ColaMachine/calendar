var Entity1124 = function (state, divId) {

var app_url = '/mersrv/ms/merchant/app/list';

var _state = state || {
app_list:[],
};

var EntityReact = React.createClass({
getInitialState: function () {
return _state;
},
componentDidMount: function(){


var _this = this;
$.ajax({
type:'GET',
url:app_url,
data:{merchantId:""},
success: function (data) {
console.log(data);
if(data.code == 0) {
_state.app_list = data.data.appList;
_this.setAuthState();
}
},
error: function () {

}
});
},
setAuthState: function () {
this.setState(_state);
console.log(_state);
},
openDetail: function (item) {
if(item.configUrl) {
window.location.href = item.configUrl;
}

},
render: function () {

var _this = this;
if(!_state.app_list){
_state.app_list=[];
}


var applist = _state.app_list.map(function (item, index) {
return <div className="module" key={index} onClick={_this.openDetail.bind(_this, item)}>
<div className="image">
<img src={item.appIcon} key={index} alt={index} />
</div>
<div className="title">{item.appName}</div>
</div>
});


return (
<div className="Entity1124">
<div className="container">
{applist}
</div>
</div>
);
}
});





/**
* 制作页面时返回React对象
* @returns {*}
*/
function render() {
return ReactDOM.render(<EntityReact />, document.getElementById(divId));
}

return {
render: render
}
};
