import React from "react";
import firebase from "../../firebase";
import { connect } from "react-redux";
import { setCurrentChannel, setPrivateChannel, setMessages } from "../../actions";
import { Menu, Icon } from "semantic-ui-react";
import { throws } from "assert";

class DirectMessages extends React.Component {
  state = {
    activeChannel: "",
    user: this.props.currentUser,
    users: [],
    usersRef: firebase.database().ref("users"),
    connectedRef: firebase.database().ref(".info/connected"),
    presenceRef: firebase.database().ref("presence")
  };

  componentDidMount() {
    if (this.state.user) {
      this.addListeners(this.state.user.userid);
    }

  }

  addListeners = currentUserUid => {
    let loadedUsers = [];

    var url = 'http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/users/getallusers'
    fetch(url, {
      method: 'get',
      credentials : 'include',
    })
    .then(response => response.json())
    .then(users => {
      console.log("LIST of USERS = "+ JSON.stringify(users))
    this.setState({users : users});
    })

    
  }

    


  /*  this.state.usersRef.on("child_added", snap => {
      if (currentUserUid !== snap.key) {
        let user = snap.val();
        user["uid"] = snap.key;
        user["status"] = "offline";
        loadedUsers.push(user);
        this.setState({ users: loadedUsers });
      }
    });

  /* == this.state.connectedRef.on("value", snap => {
      if (snap.val() === true) {
        const ref = this.state.presenceRef.child(currentUserUid);
        ref.set(true);
        ref.onDisconnect().remove(err => {
          if (err !== null) {
            console.error(err);
          }
        });
      }
    });
    this.state.presenceRef.on("child_added", snap => {
      if (currentUserUid !== snap.key) {
        this.addStatusToUser(snap.key);
      }
    });

    this.state.presenceRef.on("child_removed", snap => {
      if (currentUserUid !== snap.key) {
        this.addStatusToUser(snap.key, false);
      }
    });
    */
  //};

  addStatusToUser = (userId, connected = true) => {
    const updatedUsers = this.state.users.reduce((acc, user) => {
      if (user.uid === userId) {
        user["status"] = `${connected ? "online" : "offline"}`;
      }
      return acc.concat(user);
    }, []);
    this.setState({ users: updatedUsers });
  };

  isUserOnline = user => user.status === "online";

  changeChannel = user => {
    console.log("changeChannel = " + JSON.stringify(user))
    const channelId = this.getChannelId(user.userid);
    const channelData = {
      id: channelId,
      name: user.userid
    };

    this.props.setCurrentChannel(channelData);
    this.props.setPrivateChannel(true);
    this.setActiveChannel(user.userid);
    localStorage.setItem("TEST",user.userid);
    this.props.setMessages([])

    var url = 'http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/getdm?sender='+localStorage.getItem('username')+'&&receiver='+ localStorage.getItem("TEST");
      fetch(url, {
        method: 'get',
        credentials : 'include',
      })
     .then(Response => {
      Response.json()
      .then(message => {
      console.log("VALUE"+message)
      this.props.setMessages(message)
      })
    })

  };

  getChannelId = userId => {
    const currentUserId = this.state.user.username
    console.log("currentUserId = " + JSON.stringify(this.state.user))
    return userId < currentUserId
      ? `${userId}/${currentUserId}`
      : `${currentUserId}/${userId}`;
  };

  setActiveChannel = userId => {
    this.setState({ activeChannel: userId });
  };

  render() {
    const { users, activeChannel } = this.state;

    return (
      <Menu.Menu className="menu">
        <Menu.Item>
          <span>
            <Icon name="mail" /> DIRECT MESSAGES
          </span>{" "}
          ({users.length})
        </Menu.Item>
        {users.map(user => (
          <Menu.Item
            key={user.userid}
            active={user.userid === activeChannel}
            onClick={() => this.changeChannel(user)}
            style={{ opacity: 0.7, fontStyle: "italic" }}
          >
            <Icon
              name="circle"
              color={this.isUserOnline(user) ? "green" : "red"}
            />
            @ {user.userid}
          </Menu.Item>
        ))}
      </Menu.Menu>
    );
  }
}

export default connect(
  null,
  { setCurrentChannel, setPrivateChannel, setMessages }
)(DirectMessages);
