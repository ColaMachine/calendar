
import React from 'react'
import {Button, Table, Modal, Pagination, notification,Form,Input} from 'antd';
//import {Link} from 'react-router-dom'
import util from '../../../util/formatTool'
//import  Axios from 'axios';
//import  QueryString from 'querystring';
//import $http from '../../../util/http';
import { setTimeout } from 'timers';
let data=[]
const FormItem = Form.Item;//重新命名
class VersionList extends React.Component {
    constructor() {
        super();
        this.state = {
           err:true
        }
        this.onEdit=this.onEdit.bind(this);

    }

    componentWillMount() {
        this.HeaderToken = {headers: {token: sessionStorage.token}};
    }

    componentDidMount() {
       
    }
    handleSubmit = (e) => {console.log("提交按钮");
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {//验证  与 validateFields 相似，但校验完后，如果校验不通过的菜单域不在可见范围内，则自动滚动进可见范围
            //console.log("是否有错误",err);
            if (!err) {
               let title=values.remark;//获取标题
               setTimeout(()=>{//如果没有错误的话 就onEdit 跳转
                    this.onEdit(title);
                },300)//过300秒跳转onedit
            }
        });
    }
    onEdit=(title)=>{//alert("onEdit");
         let url="/index/investigateManager/add?"+"title="+`${title}`//跳转增加界面
         window.location.hash=url;//hash改变 路由变换
    }
    render() {//渲染函数
        const {getFieldDecorator} = this.props.form;
        return (
            <Form onSubmit={this.handleSubmit} layout='vertical' style={{'maxWidth': '750px'}}>
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>创建问卷</div>
                <span>问卷名称:</span>
                <FormItem >
                    {getFieldDecorator('remark', {
                        rules: [{required: true, message: '必填!'}],
                    })(
                        <Input placeholder='请输入问卷名称' style={{width:"1208px"}}/>
                    )}
                </FormItem>
                <div className="form">
                    <Button type="primary" className="storageButton" htmlType="submit" 
                    >录入题目</Button>
                </div>
                
            </div>
            </Form>
        );
    }
}
const user_AppForm = Form.create()(VersionList);
export default user_AppForm
