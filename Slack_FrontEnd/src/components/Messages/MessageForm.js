import React from "react";
import uuidv4 from "uuid/v4";
import firebase from "../../firebase";
import { Segment, Button, Input } from "semantic-ui-react";
import { setMessages } from "../../actions";
import FileModal from "./FileModal";
import ProgressBar from "./ProgressBar";
import { connect } from "react-redux";

class MessageForm extends React.Component {
  state = {
    storageRef: firebase.storage().ref(),
    uploadTask: null,
    uploadState: "",
    percentUploaded: 0,
    message: "",
    channel: this.props.currentChannel,
    user: this.props.currentUser,
    loading: false,
    errors: [],
    modal: false
  };

  openModal = () => this.setState({ modal: true });

  closeModal = () => this.setState({ modal: false });

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  createMessage = (fileUrl = null) => {
    const message = {
      timestamp: new Date().toString(),
      user: {
        username: this.state.user.username,
        avatar: this.state.user.avatar
      }
    };

    if (fileUrl !== null) {
      message["image"] = fileUrl;
    } else {
      message["messagecontent"] = this.state.message;
    }

    console.log("GENERATED MESS = " , message)
    return message;
  };

  sendMessage = () => {
    console.log("PROPS HERE = "+this.props)
    const { getMessagesRef } = this.props;
    console.log("STATE HERE = "+this.state)
    const { message, channel } = this.state;
    console.log("STATE OF MESSAGE = ", message)
    if (message) {
      this.setState({ loading: true });
      console.log("CHANNEL NAME = " + JSON.stringify(channel))
    if(this.props.isPrivateChannel){
      fetch('http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/kafka/directMessage', {
        method: 'post',
        headers: {'Content-Type':'application/json', 'Access-Control-Allow-Origin': '*'},
        credentials:'include',
        body: JSON.stringify({
          text:message,
          to:channel.name,
          from:localStorage.getItem('username'),
          timeStamp:"12 Nov"
        })})
       .then(Response => {
        Response.json()
        this.setState({ loading: false, message: "", errors: [] });
        console.log("SENT DIRECT MESSAGE")
        var url = 'http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/getdm?sender='+localStorage.getItem('username')+'&&receiver='+ localStorage.getItem("TEST");
        console.log(url)
    fetch(url, {
        method: 'get',
        credentials : 'include',
      })
     .then(Response => {
      Response.json()
      .then(message => {
      this.setState({messages:message})
      this.props.setMessages(message)
      console.log("LATEST MESSAGES = ", message)
      })
    })
      })
    }
    else{
      fetch('http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/kafka/channelMessage', {
        method: 'post',
        headers: {'Content-Type':'application/json', 'Access-Control-Allow-Origin': '*'},
        credentials:'include',
        body: JSON.stringify({
          messageContent:message,
          from:localStorage.getItem('username'),
          timestamp:"12 Nov",
          channelName:localStorage.getItem("CURRENTCHANNEL")
        })})
       .then(Response => {
        Response.json()
        this.setState({ loading: false, message: "", errors: [] });
        console.log("SENT CHANNEL MESSAGE")
        var url = 'http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/getmessages?channel=' + localStorage.getItem("CURRENTCHANNEL")
        console.log(url)
    fetch(url, {
        method: 'get',
        credentials : 'include',
      })
     .then(Response => {
      Response.json()
      .then(message => {
      this.setState({messages:message})
      this.props.setMessages(message)
      console.log("LATEST MESSAGES = ", message)
      })
    })
      })
    }
  }
    else {
      this.setState({
        errors: this.state.errors.concat({ message: "Add a message" })
      });
    }

  };

  getPath = () => {
    if (this.props.isPrivateChannel) {
      return `chat/private-${this.state.channel.id}`;
    } else {
      return "chat/public";
    }
  };

  /*uploadFile = (file, metadata) => {
    const pathToUpload = this.state.channel.id;
    const ref = this.props.getMessagesRef();
    const filePath = `${this.getPath()}/${uuidv4()}.jpg`;

    this.setState(
      {
        uploadState: "uploading",
        uploadTask: this.state.storageRef.child(filePath).put(file, metadata)
      },
      () => {
        this.state.uploadTask.on(
          "state_changed",
          snap => {
            const percentUploaded = Math.round(
              (snap.bytesTransferred / snap.totalBytes) * 100
            );
            this.setState({ percentUploaded });
          },
          err => {
            console.error(err);
            this.setState({
              errors: this.state.errors.concat(err),
              uploadState: "error",
              uploadTask: null
            });
          },
          () => {
            this.state.uploadTask.snapshot.ref
              .getDownloadURL()
              .then(downloadUrl => {
                this.sendFileMessage(downloadUrl, ref, pathToUpload);
              })
              .catch(err => {
                console.error(err);
                this.setState({
                  errors: this.state.errors.concat(err),
                  uploadState: "error",
                  uploadTask: null
                });
              });
          }
        );
      }
    );
  };*/
/*
  sendFileMessage = (fileUrl, ref, pathToUpload) => {
    ref
      .child(pathToUpload)
      .push()
      .set(this.createMessage(fileUrl))
      .then(() => {
        this.setState({ uploadState: "done" });
      })
      .catch(err => {
        console.error(err);
        this.setState({
          errors: this.state.errors.concat(err)
        });
      });
  };*/

  render() {
    // prettier-ignore
    const { errors, message, loading, modal, uploadState, percentUploaded } = this.state;
    return (
      <Segment className="message__form">
        <Input
          fluid
          name="message"
          onChange={this.handleChange}
          value={message}
          style={{ marginBottom: "0.7em" }}
          label={<Button icon={"add"} />}
          labelPosition="left"
          className={
            errors.some(error => error.message.includes("message"))
              ? "error"
              : ""
          }
          placeholder="Write your message"
        />
         <Button.Group icon widths="1"> 
          <Button
            onClick={this.sendMessage}
            disabled={loading}
            size="1000"
            color="black"
            content="Send"
            labelPosition="center"
            icon="edit"
            style={{ width: 1080, marginTop: 10 }}>
            </Button>
        {/* <Button
            color="black"
            disabled={uploadState === "uploading"}
            onClick={this.openModal}
            content="Attach Media"
            labelPosition="right"
            icon="cloud upload"
        /> */}
       </Button.Group>
        {/* <FileModal
          modal={modal}
          closeModal={this.closeModal}
          uploadFile={this.uploadFile}
        />
        <ProgressBar
          uploadState={uploadState}
          percentUploaded={percentUploaded}
        /> */}
      </Segment>
    );
  }
}

//export default MessageForm;
export default connect(
  null,
  { setMessages }
)(MessageForm);