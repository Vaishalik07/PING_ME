import React from "react";
import firebase from "../../firebase";
import { connect } from "react-redux";
import { setCurrentChannel, setPrivateChannel, setMessages } from "../../actions";
// prettier-ignore
import { Menu, Icon, Modal, Form, Input, Button, Label } from "semantic-ui-react";

class Channels extends React.Component {
  state = {
    activeChannel: "",
    user: this.props.currentUser,
    channel: null,
    channels: [],
    channelName: "",
    channelDetails: "",
    channelsRef: firebase.database().ref("channels"),
    messagesRef: firebase.database().ref("messages"),
    notifications: [],
    modal: false,
    firstLoad: true,
    messages: this.props.allmessages
  };

  componentDidMount() {
    this.addListeners();
    var url='http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/getchannels'
    fetch(url, {
        method: 'get',
        credentials : 'include',
      })
     .then(Response => {
      Response.json().then(channelList => {
      let loadedChannels = [];      
      this.setState({channels:channelList},() => this.setFirstChannel());
      console.log("LIST = " ,channelList)
      })
    })
  }

  componentWillUnmount() {
    this.removeListeners();
  }

  addListeners = () => {
    let loadedChannels = [];
   /* this.state.channelsRef.on("child_added", snap => {
      loadedChannels.push(snap.val());
      this.setState({ channels: loadedChannels }, () => this.setFirstChannel());
      this.addNotificationListener(snap.key);
    });*/
  };

  addNotificationListener = channelId => {
    this.state.messagesRef.child(channelId).on("value", snap => {
      if (this.state.channel) {
        this.handleNotifications(
          channelId,
          this.state.channel.id,
          this.state.notifications,
          snap
        );
      }
    });
  };

  handleNotifications = (channelId, currentChannelId, notifications, snap) => {
    let lastTotal = 0;

    let index = notifications.findIndex(
      notification => notification.id === channelId
    );

    if (index !== -1) {
      if (channelId !== currentChannelId) {
        lastTotal = notifications[index].total;

        if (snap.numChildren() - lastTotal > 0) {
          notifications[index].count = snap.numChildren() - lastTotal;
        }
      }
      notifications[index].lastKnownTotal = snap.numChildren();
    } else {
      notifications.push({
        id: channelId,
        total: snap.numChildren(),
        lastKnownTotal: snap.numChildren(),
        count: 0
      });
    }

    this.setState({ notifications });
  };

  removeListeners = () => {
    this.state.channelsRef.off();
  };

  setFirstChannel = () => {
    console.log("FIRSTCHANNEL= ", this.state.channels)
    const firstChannel = this.state.channels[0].channelname;

    if (this.state.firstLoad && this.state.channels.length > 0) {
      this.props.setCurrentChannel(firstChannel);
      this.setActiveChannel(firstChannel);
      this.setState({ channel: firstChannel });
    }
    this.setState({ firstLoad: false });

  };

  addChannel = () => {
    const { channelName } = this.state;

   // const key = channelsRef.push().key
   var url = "http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/addchannel?channelname="+ channelName
   console.log(url)
    fetch(url, {
        method: 'post',
        headers: {'Content-Type':'application/json', 'Access-Control-Allow-Origin': '*'},
        credentials:'include',
      })
       .then(Response => {
        this.setState({ channelName: "", channelDetails: "" });
        this.closeModal();
        console.log(Response.json());
        var url='http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/getchannels'
        fetch(url, {
          method: 'get',
          credentials : 'include',
        })
       .then(Response => {
        Response.json().then(channelList => {
        let loadedChannels = [];      
        this.setState({channels:channelList},() => this.setFirstChannel());
        console.log("LIST = " ,channelList)
        })
      })
      })

  };

  handleSubmit = event => {
    event.preventDefault();
    if (this.isFormValid(this.state)) {
      this.addChannel();
    }
  };

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  changeChannel = channel => {
    console.log("CHANGECHANNEL =", channel)
    const channelData = {
      id: channel.channelid,
      name: channel.channelname
    };

    this.setActiveChannel(channel.channelname);
    this.clearNotifications();
    this.props.setCurrentChannel(channelData);
    this.props.setPrivateChannel(false);
    this.setState({channel : channelData});
    localStorage.setItem("CURRENTCHANNEL",channel.channelname);
    console.log("CAPTURE CHANNLE=", this.props.allmessages)

    this.props.setMessages([])

    var url = 'http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/getmessages?channel=' + localStorage.getItem("CURRENTCHANNEL")
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

  

  clearNotifications = () => {
    let index = this.state.notifications.findIndex(
      notification => notification.id === this.state.channel.id
    );

    if (index !== -1) {
      let updatedNotifications = [...this.state.notifications];
      updatedNotifications[index].total = this.state.notifications[
        index
      ].lastKnownTotal;
      updatedNotifications[index].count = 0;
      this.setState({ notifications: updatedNotifications });
    }
  };

  setActiveChannel = channel => {
    this.setState({ activeChannel: channel });
  };

  getNotificationCount = channel => {
    let count = 0;

    this.state.notifications.forEach(notification => {
      if (notification.id === channel.id) {
        count = notification.count;
      }
    });

    if (count > 0) return count;
  };

  displayChannels = channels =>
    channels.length > 0 &&
    channels.map(channel => (
      <Menu.Item
        key={channel.channelid}
        onClick={() => this.changeChannel(channel)}
        name={channel.channelname}
        style={{ opacity: 0.7 }}
        active={channel.channelname === this.state.activeChannel}
      >
        {this.getNotificationCount(channel) && (
          <Label color="red">{this.getNotificationCount(channel)}</Label>
        )}
        # {channel.channelname}
      </Menu.Item>
    ));

  isFormValid = ({ channelName, channelDetails }) =>
    channelName && channelDetails;

  openModal = () => this.setState({ modal: true });

  closeModal = () => this.setState({ modal: false });

  render() {
    const { channels, modal } = this.state;

    return (
      <React.Fragment>
        <Menu.Menu className="menu">
          <Menu.Item>
            <span>
              <Icon name="exchange" /> CHANNELS
            </span>{" "}
            ({channels.length}) <Icon name="add" onClick={this.openModal} />
          </Menu.Item>
          {this.displayChannels(channels)}
        </Menu.Menu>

        {/* Add Channel Modal */}
        <Modal basic open={modal} onClose={this.closeModal}>
          <Modal.Header>Add a Channel</Modal.Header>
          <Modal.Content>
            <Form onSubmit={this.handleSubmit}>
              <Form.Field>
                <Input
                  fluid
                  label="Name of Channel"
                  name="channelName"
                  onChange={this.handleChange}
                />
              </Form.Field>

              <Form.Field>
                <Input
                  fluid
                  label="About the Channel"
                  name="channelDetails"
                  onChange={this.handleChange}
                />
              </Form.Field>
            </Form>
          </Modal.Content>

          <Modal.Actions>
            <Button color="green" inverted onClick={this.handleSubmit}>
              <Icon name="checkmark" /> Add
            </Button>
            <Button color="red" inverted onClick={this.closeModal}>
              <Icon name="remove" /> Cancel
            </Button>
          </Modal.Actions>
        </Modal>
      </React.Fragment>
    );
  }
}

export default connect(
  null,
  { setCurrentChannel, setPrivateChannel, setMessages }
)(Channels);
