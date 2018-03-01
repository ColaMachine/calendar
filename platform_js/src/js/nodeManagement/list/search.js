import React from 'react'
import {Row, Col,Table, Form,Input,Select,Button,DatePicker} from 'antd';
import $http from 'util/http'
import notice from 'util/notice'
const FormItem = Form.Item;
const Option = Select.Option;

class Search extends React.Component {
    constructor() {
        super()
        this.state = {
        	
        }
    }
    componentWillMount() {
        this.search()
    }
    componentDidMount() {
    }
    search=(e)=>{
        if (e) e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, fieldsValue) => {
           console.log("000",fieldsValue);
           this.props.searchCallback(fieldsValue)
        });
    }
    
    render() {
        const { getFieldDecorator } = this.props.form;
        const formItemLayout ={
          labelCol: { span: 11 },
          wrapperCol: { span: 13 },
        }
        const itemLayout ={
          labelCol: { span: 5 },
          wrapperCol: { span: 16 },
        }
        return ( 
                <div className="form">
                    <Form onSubmit={this.search} layout="inline">
                        <ul className="search-bar">
                            <li className="pull-right" >
                                <FormItem>
                                    <Button style={{marginLeft:'20px'}} type="primary" className="searchButton"
                                            htmlType="submit">查询</Button>
                                </FormItem>
                            </li>
                            <li className="pull-right">
                                <FormItem
                                    {...formItemLayout}
                                    label="状态"
                                >
                                {getFieldDecorator('mac4', {
                                    rules: [{required: false, message: '请输入设备MAC'}],
                                })(
                                    <Select
                                        style={{width: "95px", borderRadius: "15px"}}
                                        showSearch
                                        className="select"
                                        placeholder="请选择"
                                        optionFilterProp="children"
                                        onChange={this.statChange}
                                        onFocus={this.handleFocus}
                                        onBlur={this.handleBlur}
                                    >
                                        <Option value="1">未绑定</Option>
                                        <Option value="2">已绑定</Option>
                                    </Select>
                                )}
                                </FormItem>
                            </li>
                            <li className="pull-right">
                                <FormItem
                                    {...formItemLayout}
                                    label="县"
                                >
                                {getFieldDecorator('mac3', {
                                    rules: [{required: false, message: '请输入设备MAC'}],
                                })(
                                    <Select
                                        style={{width: "95px", borderRadius: "15px"}}
                                        showSearch
                                        className="select"
                                        placeholder="请选择"
                                        optionFilterProp="children"
                                        onChange={this.statChange}
                                        onFocus={this.handleFocus}
                                        onBlur={this.handleBlur}
                                    >
                                        <Option value="1">未绑定</Option>
                                        <Option value="2">已绑定</Option>
                                    </Select>
                                )}
                                </FormItem>
                            </li>
                            <li className="pull-right">
                                <FormItem
                                    {...formItemLayout}
                                    label="市"
                                >
                                {getFieldDecorator('mac2', {
                                    rules: [{required: false, message: '请输入设备MAC'}],
                                })(
                                    <Select
                                        style={{width: "95px", borderRadius: "15px"}}
                                        showSearch
                                        className="select"
                                        placeholder="请选择"
                                        optionFilterProp="children"
                                        onChange={this.statChange}
                                        onFocus={this.handleFocus}
                                        onBlur={this.handleBlur}
                                    >
                                        <Option value="1">未绑定</Option>
                                        <Option value="2">已绑定</Option>
                                    </Select>
                                )}
                                </FormItem>
                            </li>
                            <li className="pull-right">
                                <FormItem
                                    {...formItemLayout}
                                    label="省"
                                >
                                {getFieldDecorator('mac1', {
                                    rules: [{required: false, message: '请输入设备MAC'}],
                                })(
                                    <Select
                                        style={{width: "95px", borderRadius: "15px"}}
                                        showSearch
                                        className="select"
                                        placeholder="请选择"
                                        optionFilterProp="children"
                                        onChange={this.statChange}
                                        onFocus={this.handleFocus}
                                        onBlur={this.handleBlur}
                                    >
                                        <Option value="1">未绑定</Option>
                                        <Option value="2">已绑定</Option>
                                    </Select>
                                )}
                                </FormItem>
                            </li>
                            <li className="pull-right">
                                <FormItem
                                    {...formItemLayout}
                                    label="名称"
                                >{getFieldDecorator('mac', {
                                    rules: [{required: false, message: '请输入设备MAC'}],
                                })(
                                    <Input style={{width:120}} placeholder="请输入"/>
                                )}
                                </FormItem>
                            </li>
                        </ul>
                    </Form>

                    <div style={{clear: "both"}}></div>
                </div>
        );
    }
}
const ops_Search = Form.create()(Search);
export default ops_Search