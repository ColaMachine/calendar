var Entity1125 = function (state, divId) {

    var merchant_url = '/mersrv/ms/merchant/';

    var _state = state || {
            merchant_name:'',
            merchant_msg:'',
            merchant_address:'',
            merchant_telephone:'',
            merchant_opentime:'',
            merchant_closetime:''
        };

    var EntityReact = React.createClass({
        getInitialState: function () {
            // console.log(_state);
            return _state;
        },
        componentDidMount: function(){
            $("[data-toggle='popover']").popover({
                trigger: 'hover',
                container: 'body'
            });


            //上传至正式环境测试时显示如下代码
            if(_DEV_ID == '{@devId@}') {
                return;
            }

            var _this = this;
            $.ajax({
                type:'GET',
                url:merchant_url + _CUSTOMER_ID,
                data:{},
                success: function (data) {
                    console.log(data);
                    if(data.code == 0) {
                        var temp = data.data.merchant;
                        _state.merchant_name = temp.merchantName;
                        _state.merchant_msg = temp.remark;
                        _state.merchant_address = temp.address;
                        _state.merchant_telephone = temp.telephone;
                        _state.merchant_opentime = temp.openTime;
                        _state.merchant_closetime = temp.closeTime;
                        _this.setAuthState();
                    }else {

                    }
                },
                error: function () {

                }
            });

        },
        //更新组件状态
        setAuthState: function () {
            this.setState(_state);
            console.log(_state);
        },
        render: function () {
            return (
            <div className="Entity1125">
                <div className="container">
                    <div className="merchant_name">{_state.merchant_name ? _state.merchant_name : '暂无'}</div>
                    <div className="merchant_info">{_state.merchant_msg ? _state.merchant_msg : '暂无'}</div>
                    <div className="merchant_iconlist">
                       <div> <div className="location" data-toggle="popover" data-placement="auto top" data-content={_state.merchant_address ? _state.merchant_address : '暂无'}></div>
                       </div>
                        <div><div className="telephone" data-toggle="popover" data-placement="auto top" data-content={_state.merchant_telephone ? _state.merchant_telephone : '暂无'}></div>
                          </div>
                       <div> <div className="time" data-toggle="popover" data-placement="auto top" data-content={_state.merchant_opentime || _state.merchant_closetime ? _state.merchant_opentime + '~' + _state.merchant_closetime : '暂无'}></div>
                        </div>
                    </div>
                </div>
            </div>
            );
        }
    });


    // OtherReact 在这里定义

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