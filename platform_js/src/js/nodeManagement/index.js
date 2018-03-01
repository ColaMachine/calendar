/**
 * Created by Stanley Huang 2017.12.21
 */
import React from 'react'
import {Route } from 'react-router-dom'
 import List from './list'
import Add from './add'
import Edit from './edit'

import {
    HashRouter
} from 'react-router-dom'
import {withRouter} from 'react-router-dom';

 class InvestigateManager extends React.Component {
    constructor() {
        super();
    }

     componentDidMount() {
         console.log('InvestigateManager mount:',this.props )
     }

    routerWillLeave=(nextLocation)=> {
        console.log('routerWillLeave:', nextLocation);
        alert(1);

    }
    render() {
        return (
            <HashRouter  routerWillLeave={this.routerWillLeave}>
            <div >
                <Route path="/index/nodeManagement/list" component={List}  routerWillLeave={this.routerWillLeave}/>
                <Route path="/index/nodeManagement/add" component={Add}/>
                <Route path="/index/nodeManagement/edit" component={Edit}/>
            </div>
            </HashRouter>
        );
    }
}

export default withRouter(InvestigateManager);