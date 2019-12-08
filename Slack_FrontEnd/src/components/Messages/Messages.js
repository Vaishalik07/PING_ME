import React from "react";
import { Segment, Comment } from "semantic-ui-react";
import firebase from "../../firebase";
import { setMessages } from "../../actions";
import MessagesHeader from "./MessagesHeader";
import MessageForm from "./MessageForm";
import Message from "./Message";
import {Button} from 'semantic-ui-react';
import { throws } from "assert";
import { connect } from "react-redux";

class Messages extends React.Component {

    //this.sayHello = this.sayHello.bind(this);
  state = {
    privateChannel: this.props.isPrivateChannel,
    privateMessagesRef: firebase.database().ref("privateMessages"),
    messagesRef: firebase.database().ref("messages"),
    messages: this.props.allmessages,
    messagesLoading: true,
    channel: this.props.currentChannel,
    isChannelStarred: false,
    user: this.props.currentUser,
    usersRef: firebase.database().ref("users"),
    numUniqueUsers: "",
    searchTerm: "",
    searchLoading: false,
    searchResults: [],
    loadedOnce: false
  
  };

  componentDidMount()
  {
    const { messages , channel} = this.state;
    var self = this
    console.log(this.state.user.username)
    if(this.props.isPrivateChannel) {
      var url = 'http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/getdm?sender='+localStorage.getItem('username')+'&&receiver='+ localStorage.getItem("TEST");
      fetch(url, {
        method: 'get',
        credentials : 'include',
      })
     .then(Response => {
      Response.json()
      .then(message => {
      console.log("VALUE"+message)
      this.setState({messages:message})
      this.props.setMessages(message)
      console.log(messages)
      })
    })
    }
    else {
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
    console.log(messages)
    })
  })
}
  }


  getMessagesRef = () => {
    const { messagesRef, privateMessagesRef, privateChannel } = this.state;
    return privateChannel ? privateMessagesRef : messagesRef;
  };

  handleStar = () => {
    this.setState(
      prevState => ({
        isChannelStarred: !prevState.isChannelStarred
      }),
      () => this.starChannel()
    );
  };

  starChannel = () => {
    if (this.state.isChannelStarred) {
      this.state.usersRef.child(`${this.state.user.uid}/starred`).update({
        [this.state.channel.channelid]: {
          name: this.state.channel.channelname,
          details: this.state.channel.details,
          createdBy: {
            name: this.state.channel.createdBy.name,
            avatar: this.state.channel.createdBy.avatar
          }
        }
      });
    } else {
      this.state.usersRef
        .child(`${this.state.user.uid}/starred`)
        .child(this.state.channel.id)
        .remove(err => {
          if (err !== null) {
            console.error(err);
          }
        });
    }
  };

  handleSearchChange = event => {
    this.setState(
      {
        searchTerm: event.target.value,
        searchLoading: true
      },
      () => this.handleSearchMessages()
    );
  };

  handleSearchMessages = () => {
    const channelMessages = [...this.state.messages];
    const regex = new RegExp(this.state.searchTerm, "gi");
    const searchResults = channelMessages.reduce((acc, message) => {
      if (
        (message.content && message.content.match(regex)) ||
        message.user.name.match(regex)
      ) {
        acc.push(message);
      }
      return acc;
    }, []);
    this.setState({ searchResults });
    setTimeout(() => this.setState({ searchLoading: false }), 1000);
  };

  countUniqueUsers = messages => {
    const uniqueUsers = messages.reduce((acc, message) => {
      if (!acc.includes(message.user.name)) {
        acc.push(message.user.name);
      }
      return acc;
    }, []);
    const plural = uniqueUsers.length > 1 || uniqueUsers.length === 0;
    const numUniqueUsers = `${uniqueUsers.length} user${plural ? "s" : ""}`;
    this.setState({ numUniqueUsers });
  };

  updateMessages ()
  {
    var url = 'http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/getdm?sender=' + localStorage.getItem('username') + '&&receiver='+ localStorage.getItem("TEST");
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

  }

  displayMessages = messages => {
  
  }


  displayChannelName = channel => {
    console.log("CHANNEL VALUE= ", channel)
    if(channel)
    {
        if(this.state.privateChannel)
        {
           return "@"+channel.name
        }
        else
        {
          return "#"+channel.name
        }
    }
    else
    {
        return "";
    }
  };

  render() {
    // prettier-ignore
    const { messagesRef, messages, channel, user, numUniqueUsers, searchTerm, searchResults, searchLoading, privateChannel, isChannelStarred } = this.state;

    return (
      <React.Fragment>
        <MessagesHeader
          channelName={this.displayChannelName(channel)}
          numUniqueUsers={numUniqueUsers}
          handleSearchChange={this.handleSearchChange}
          searchLoading={searchLoading}
          isPrivateChannel={privateChannel}
          handleStar={this.handleStar}
          isChannelStarred={isChannelStarred}
        />
       
        <Segment>
          <Comment.Group className="messages">
         {
             this.props.allmessages.map((message) => {
             return (
          <Message
            key={message.timestamp}
            message={message}
            user={this.state.user}   
            />
        );
      })
      }
         {/*this.displayMessages(this.props.allmessages)*/}
          </Comment.Group>
        </Segment>
        <MessageForm
          // messagesRef={messagesRef}
          currentChannel={channel}
          currentUser={user}
          isPrivateChannel={privateChannel}
          allmessages={messages}
          // getMessagesRef={this.getMessagesRef}
        />
      </React.Fragment>
      
    );
  }
}

//export default Messages;
export default connect(
  null,
  { setMessages }
)(Messages);