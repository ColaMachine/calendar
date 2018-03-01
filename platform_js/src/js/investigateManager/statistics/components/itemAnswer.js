import React from 'react';
import ReactDOM from 'react-dom';
import  Axios from 'axios';
import {Button, Table, Modal, Pagination, notification} from 'antd';
export default class Answeroption extends React.Component{
    constructor(props){
        super(props)
        this.state={
            visible: false,
            id:0,
            type:''
        }
        let type=this.props.type;
        this.setState({
            type:type
        })
    }
    componentWillMount(){
       
    }
   
    doDelete=()=>{
        let url = '/questionnaire/answer';
        url = url +`/${this.state.id}`;
        Axios.delete(url, this.HeaderToken).then(({data}) => {
           if(data.code==0){
            setTimeout(() => {
                this.setState({
                    visible: false,
                })
             if(this.props.data.type==1){
                    this.props.search(this.props.id,1)
                }else{
                    this.props.search(this.props.id,2)
                }  
            }, 800);
           
           }
        }).catch((err) => {
           
        })
    }
    showModal=(id)=>{
        this.setState({
            visible: true,
            id
        });
    }
    handleOk = (e) => {
        console.log(e);
        this.setState({
          visible: false,
        });
      }
    handleCancel = (e) => {
        console.log(e);
        this.setState({
            visible: false,
        })
    }
    Employment=(item,type)=>{
       console.log(type);
       if(this.props.data.type!=1){
            let url ='/questionnaire/answer/ordinary';
            url = url +`/${item}`;
            Axios.put(url, this.HeaderToken).then(({data}) => {
                if(data.code==0){
                    notification['success']({
                        message: '提示',
                        description: `${this.props.data.type==1?"录入成功":"返出成功"}`,
                    });
                }

                if(this.props.type==2){
                        this.props.search(this.props.id,1)
                }else{
                        this.props.search(this.props.id,this.props.type)
                }
                
                }).catch((err) => {
                    
                })
       }else{
            let url ='/questionnaire/answer/excellent';
            url = url +`/${item}`;
            Axios.put(url, this.HeaderToken).then(({data}) => {
            if(data.code==0){
                notification['success']({
                    message: '提示',
                    description: `${this.props.data.type==1?"录入成功":"返出成功"}`,
                });
            }
            if(this.props.type==2){
                    this.props.search(this.props.id,1)
            }else{
                    this.props.search(this.props.id,this.props.type)
            }
            
            }).catch((err) => {
                
            })
       }


    }
    parameters=()=>{
        setTimeout(() => {
            console.log("this.props.data",this.props.pageSize);
            let url='questionnaire/statistics/answers'+ '?'+`id=1`+"&"+`pageNum=${this.props.pageNum}`+"&"+`pageSize=${this.props.pageSize}`;
            this.props.search(url);
        }, 100);
    }
    render(){
        console.log('from syayrasda',this.state)
        return (
            <div>
                <span>用户昵称:{this.props.data.username}</span>
                <span style={{marginLeft:"30px"}}>账号:{this.props.data.userAccount}</span>
                <div>{this.props.data.answer}</div>
                <div className="quest-title">
                <div className="line"></div>
                <Button type="primary"  onClick={this.Employment.bind(this,this.props.data.id,this.props.data.type)}>{this.props.data.type==1?"录入":"差建议"}</Button>
                <Button type="primary"  style={{marginLeft:"10px"}} onClick={this.showModal.bind(this,this.props.data.id)}>删除</Button>
                </div>
                <Modal
                    title="提示"
                    visible={this.state.visible}
                    footer={[
                        <Button key="back" size="large" onClick={this.handleCancel}>取消</Button>,
                        <Button key="submit" type="primary" size="large" 
                                onClick={this.doDelete}>确定</Button>
                    ]}
                    onOk={this.handleOk}
                    onCancel={this.handleCancel}
                    >
                    <p>
                    <span>确定要删除答案!</span>
                    <span style={{display:'block'}}>{this.props.data.answer}</span>
                    </p>
                </Modal>
            </div>
        );
    }
}