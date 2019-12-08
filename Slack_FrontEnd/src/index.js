import React from "react";
import ReactDOM from "react-dom";
import App from "./components/App";
import Login from "./components/Auth/Login";
import Register from "./components/Auth/Register";
import Spinner from "./Spinner";
import registerServiceWorker from "./registerServiceWorker";
import firebase from "./firebase";

import "semantic-ui-css/semantic.min.css";

import {
  BrowserRouter as Router,
  Switch,
  Route,
  withRouter
} from "react-router-dom";

import { createStore } from "redux";
import { Provider, connect } from "react-redux";
import { composeWithDevTools } from "redux-devtools-extension";
import rootReducer from "./reducers";
import { setUser, clearUser } from "./actions";

const store = createStore(rootReducer, composeWithDevTools());

class Root extends React.Component {
  constructor (props) {
    super(props)
    this.state ={
        user: {
       emailId:'',
       username:'',
       avatar:'',
       
      }
    }
    this.loadUser = this.loadUser.bind(this)
  }

  loadUser(data)
  {
    if(localStorage.getItem('loggedin') == "true"){
      var user = { emailId: localStorage.getItem('emailId') , username : localStorage.getItem('username') ,  avatar : localStorage.getItem('avatar')}
      this.props.setUser(data);
       this.setState({refresh:false});
      this.props.history.push("/slack");
      } else {
       this.props.history.push("/login");
       this.props.clearUser();
      }
  }

  componentDidMount() {
    this.setState({refresh:true});   
    if(localStorage.getItem('loggedin') == "true"){
      var user = { emailId: localStorage.getItem('emailId') , username : localStorage.getItem('username') ,  avatar : localStorage.getItem('avatar')}
      this.props.setUser(user);
       this.setState({refresh:false});
      this.props.history.push("/slack");
      } else {
       this.props.history.push("/login");
       this.props.clearUser();
      }
  }

  
  render() {
    return this.props.isLoading ? (
      <Spinner />
    ) : (
      <Switch>
        <Route exact path="/slack" component={App} />
        <Route path="/login" render={()=>(<Login loadUser={this.loadUser}/>)} />
        <Route path="/register" render={()=>(<Register loadUser={this.loadUser}/>)} />
      </Switch>
    );
  }
}

const mapStateFromProps = state => ({
  isLoading: state.user.isLoading
});

const RootWithAuth = withRouter(
  connect(
    mapStateFromProps,
    { setUser, clearUser }
  )(Root)
);

ReactDOM.render(
  <Provider store={store}>
    <Router>
      <RootWithAuth />
    </Router>
  </Provider>,
  document.getElementById("root")
);
registerServiceWorker();
