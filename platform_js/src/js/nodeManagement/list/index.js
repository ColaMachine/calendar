/**
 * 版本升级管理列表
 */
import React from 'react'
import {Button, Table, Modal, Pagination, notification,Row,Col} from 'antd';
import {Link} from 'react-router-dom'
import Search from './search'
import util from '../../../util/formatTool'
import  Axios from 'axios';
import  QueryString from 'querystring';
import $http from '../../../util/http';


export default class VersionList extends React.Component {
    constructor() {
        super();
        this.state = {
            tableData: [],
            registerWindow: false,
            submitting: false,
            showDelModal: false,
            total: 0
        }
        this.onPageSizeChange = this.onPageSizeChange.bind(this);
        this.onPageChange = this.onPageChange.bind(this);

        this.pageNo = 1;
        this.pageSize = 10;

    }

    componentWillMount() {
        this. onSearchCallback()
        this.HeaderToken = {headers: {token: sessionStorage.token}};
    }

    componentDidMount() {
       
    }

    onPageSizeChange(current, size) {
        this.pageSize = size;
        this.pageNo = current;
        this.search();
    }

    onPageChange(page, pageSize) {
        this.pageNo = page;
        this.search();
    }
    onSearchCallback(data){

        console.log("from search",data)

    }
    pageItemRender(current, type, originalElement) {
        if (type === 'prev') {
            return <a>上一页</a>;
        } else if (type === 'next') {
            return <a>下一页</a>;
        }
        return originalElement;
    }

    render() {
        const columns = [
            {
                title: '节点名称',
                dataIndex: 'corporation',
            }, {
                title: '地区',
                dataIndex: 'model',
            }, {
                title: 'IP',
                dataIndex: 'version',
            }, {
                title: '端口',
                dataIndex: '$uploadDate',
            }, {
                title: '状态',
                dataIndex: 'versionDescription',
            }, {
                title: '编辑',
                width: '10%',
                dataIndex: 'versionDescription0',
                render: (text, record, index) => {
                    return <span><Link to="/index/nodeManagement/edit">编辑</Link></span>
                }
            },{
                title: '一键恢复',
                dataIndex: 'versionDescription1',
            },{
                title: '描述',
                dataIndex: 'versionDescription2',
            },{
                title: '添加时间',
                dataIndex: 'versionDescription3',
            }
        ];

        return (
            <div className="right-management">
                <div className="page-title-me" style={{width: '100%'}}>节点管理</div>
                <Row  type="flex" justify="space-between" align="middle">
                    <Col span={4}>
                    <div className="form">
                        <Button type="primary" className="storageButton"
                        ><Link to="/index/nodeManagement/add">添加节点</Link></Button>
                    </div>
                    </Col>
                    <Col span={20}>
                         <Search searchCallback={this.onSearchCallback} />
                    </Col>
                </Row>
                <div className="table">
                    <Table columns={columns} className="dev-table"
                           style={{backgroundColor: '#fff'}}
                           dataSource={this.state.tableData}
                           pagination={false}/>
                </div>
                {/*分页*/}
                {this.state.total ? <Pagination
                    className="pagination"
                    showSizeChanger
                    onShowSizeChange={this.onPageSizeChange}
                    onChange={this.onPageChange}
                    total={this.state.total}
                    itemRender={this.pageItemRender}/> : ''}
            </div>
        );
    }
}

