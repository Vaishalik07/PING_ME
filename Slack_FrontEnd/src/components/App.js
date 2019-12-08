import React from "react";
import { Grid } from "semantic-ui-react";
import "./App.css";
import { connect } from "react-redux";

import ColorPanel from "./ColorPanel/ColorPanel";
import SidePanel from "./SidePanel/SidePanel";
import Messages from "./Messages/Messages";
import MetaPanel from "./MetaPanel/MetaPanel";

const App = ({ currentUser, currentChannel, isPrivateChannel, allmessages }) => (
  <Grid columns="equal" className="app" style={{ background: "#FFF" }}>
    {/*<ColorPanel />*/}
    <SidePanel key={currentUser && currentUser.uid} currentUser={currentUser} />

    <Grid.Column style={{ marginLeft: 320 }} width={8}>
      <Messages
        key={currentChannel && currentChannel.id}
        currentChannel={currentChannel}
        currentUser={currentUser}
        isPrivateChannel={isPrivateChannel}
        allmessages={allmessages}
      />
    </Grid.Column>

    {/* <Grid.Column width={4}>
    {/* <MetaPanel
        key={currentChannel && currentChannel.id}
        currentChannel={currentChannel}
        isPrivateChannel={false}
    />
   </Grid.Column> */}
  </Grid>
);

const mapStateToProps = state => ({
  currentUser: state.user.currentUser,
  currentChannel: state.channel.currentChannel,
  isPrivateChannel: state.channel.isPrivateChannel,
  allmessages : state.messages.allmessages
});

export default connect(mapStateToProps)(App);
