import React from 'react'
import {Row, Col, Table, Form, Input, Select, Button, Modal} from 'antd';
import $http from 'util/http'
import $route from 'util/routeTool'
//import '../../css/commForm.scss'
const FormItem = Form.Item;
const Option = Select.Option;
const params = $route.query();
const sign = $route.params()[1];
console.log(params.id)
class userForm extends React.Component {
    constructor() {
        super()
        this.state = {
           
        }
    }

    componentWillMount() {
		
    }
	
    componentDidMount() {
    }
	
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
	           if(!err){
                    let yy=Object.assign(values,this.state.e)
                    console.log("yy",values);
               }
	         
        });
    }
    Option=(e)=>{
       this.setState({
           e
       })
    }
    goBack = () => {
        window.history.back();
    }
    checkOrganization = (rule, value, callback) => {
       
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        const formItemLayout ={
            labelCol: { span: 2 },
            wrapperCol: { span: 13 },
          }
        return (
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>添加节点</div>
            <Form onSubmit={this.handleSubmit} layout='vertical' style={{'maxWidth': '750px',marginLeft:"30px"}}>
                    <FormItem {...formItemLayout} label="节点名称:">
                        {getFieldDecorator('mac0', {
                            rules: [{required: true, message: '必填', whitespace: true}],
                        })(
                            <Input placeholder='请输入' />
                        )}
                    </FormItem>
                    <FormItem {...formItemLayout} label="地区:">
                            {getFieldDecorator('B', {
                                rules: [{required: true, message: '必填!', whitespace: true}],
                                
                            })(
                                <Select
                                style={{width: "180px"}}
                                showSearch
                                className="select"
                                placeholder="省"
                                optionFilterProp="children"
                                onChange={this.statChange}
                                onFocus={this.handleFocus}
                                onBlur={this.handleBlur}
                            >
                                <Option value="1">未绑定</Option>
                                <Option value="2">已绑定</Option>
                            </Select>
                        )}
                        <Select
                            style={{width: "180px",marginLeft:"40px"}}
                            showSearch
                            className="select"
                            placeholder="市"
                            optionFilterProp="children"
                            onChange={this.Option}
                            onFocus={this.handleFocus}
                            onBlur={this.handleBlur}
                        >
                            <Option value="1">未绑定</Option>
                            <Option value="2">已绑定</Option>
                        </Select>
                    </FormItem>
                    <FormItem {...formItemLayout} label="服务IP:">
                        {getFieldDecorator('mac3', {
                            rules: [{required: true, message: '必填!', whitespace: true}],
                        })(
                            <Input placeholder='请输入' />
                        )}
                    </FormItem>
                    <FormItem {...formItemLayout} label="端口:">
                        {getFieldDecorator('mac4', {
                            rules: [{required: true, message: '必填!', whitespace: true}],
                        })(
                            <Input placeholder='请输入' />
                        )}
                    </FormItem>
                    <FormItem {...formItemLayout} label="描述:">
                        {getFieldDecorator('mac5', {
                            rules: [{required: false, message: '请输入设备MAC'}],
                        })(
                            <Input.TextArea placeholder="节点描述" autosize={{minRows: 2, maxRows: 6}} />
                        )}
                    </FormItem>
                    <div className="btn-area">
                    <Button type="primary" htmlType="submit" className="storageButton"
                           style={{marginLeft:"60px"}}>保存</Button>
                    <Button
                        className="cancel"
                        onClick={this.goBack} style={{width:"140px",marginLeft:"124px"}}>取 消</Button>
                </div>
            </Form>
            </div>
        );
    }
}
const user_AppForm = Form.create()(userForm);
export default user_AppForm