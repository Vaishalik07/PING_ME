import React from "react";
import moment from "moment";
import { Comment, Image } from "semantic-ui-react";

const isOwnMessage = (message, user) => {
  return message.sender === user.username ? "message__self" : "";
};

const isImage = message => {
  return message.hasOwnProperty("image") && !message.hasOwnProperty("content");
};

const timeFromNow = timestamp => moment(timestamp).fromNow();

const Message = ({ message, user }) => (
  <Comment>
    <Comment.Avatar src={message.avatar} />
    <Comment.Content className={isOwnMessage(message, user)}>
      <Comment.Author as="a">{message.sender}</Comment.Author>
      {isImage(message) ? (
        <Image src={message.image} className="message__image" />
      ) : (
        <Comment.Text>{message.messagecontent}</Comment.Text>
      )}
    </Comment.Content>
  </Comment>
);

export default Message;
