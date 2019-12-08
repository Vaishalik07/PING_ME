import React from "react";
import firebase from "../../firebase";
import md5 from "md5";
import {
  Grid,
  Form,
  Segment,
  Button,
  Header,
  Message,
  Icon
} from "semantic-ui-react";
import { Link } from "react-router-dom";

class Register extends React.Component {
  state = {
    username: "",
    email: "",
    password: "",
    passwordConfirmation: "",
    firstname:"",
    lastname:"",
    errors: [],
    loading: false,
    usersRef: firebase.database().ref("users")
  };

  isFormValid = () => {
    let errors = [];
    let error;

    if (this.isFormEmpty(this.state)) {
      error = { message: "Fill in all fields" };
      this.setState({ errors: errors.concat(error) });
      return false;
    } else if (!this.isPasswordValid(this.state)) {
      error = { message: "Password is invalid" };
      this.setState({ errors: errors.concat(error) });
      return false;
    } else {
      return true;
    }
  };

  isFormEmpty = ({ username, email, password, passwordConfirmation }) => {
    return (
      !username.length ||
      !email.length ||
      !password.length ||
      !passwordConfirmation.length
    );
  };

  isPasswordValid = ({ password, passwordConfirmation }) => {
    if (password.length < 6 || passwordConfirmation.length < 6) {
      return false;
    } else if (password !== passwordConfirmation) {
      return false;
    } else {
      return true;
    }
  };

  displayErrors = errors =>
    errors.map((error, i) => <p key={i}>{error.message}</p>);

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  handleSubmit = event => {
    event.preventDefault();
      var self=this;
      if (this.isFormValid(this.state)) {
        this.setState({ errors: [], loading: true });


	firebase
        .auth()
        .createUserWithEmailAndPassword(this.state.email, this.state.password)
        .then(createdUser => {
          console.log(createdUser);
          createdUser.user
            .updateProfile({
              displayName: this.state.username,
              photoURL: `http://gravatar.com/avatar/${md5(
                createdUser.user.email
              )}?d=identicon`
            })
            .then(() => {
              this.saveUser(createdUser).then(() => {
                console.log("user saved");
              });
            })
            .catch(err => {
              console.error(err);
              this.setState({
                errors: this.state.errors.concat(err),
                loading: false
              });
            });
        })
        .catch(err => {
          console.error(err);
          this.setState({
            errors: this.state.errors.concat(err),
            loading: false
          });
        });


        var img_url = "gravatar.com/"+this.state.username
        fetch('http://BackendLoadBalancer-1314010567.us-west-1.elb.amazonaws.com:3000/api/users/signup', {
          method: 'post',
          headers: {'Content-Type':'application/json', 'Access-Control-Allow-Origin': '*'},
          credentials:'include',
          body: JSON.stringify({
            emailId : this.state.email,
            userId : this.state.username,
            firstName : this.state.firstname,
            lastName : this.state.lastname,
            password : this.state.password,
            imageUrl : img_url
          })})
         .then(signedInUser => {
          console.log(signedInUser);
          signedInUser.json().then(user => {
            console.log("NAME - " + JSON.stringify(user))
            console.log(self.state.email + "logged in")
            localStorage.setItem('loggedin','true');
            localStorage.setItem('emailId',self.state.email);
            localStorage.setItem('avatar',user[0].avatar);
            localStorage.setItem('username',user[0].userid);
            self.setState({Redirect: true});
            self.props.loadUser(user[0])
           // self.setUser(user)
          })
    
         /* else {
              this.setState({
              errors: this.state.errors.concat("Login Failed"),
              loading: false
            });
            localStorage.setItem('loggedin','false');
            this.setState({Redirect: false});
         }*/
          })
    }};
  

  saveUser = createdUser => {
    return this.state.usersRef.child(createdUser.user.uid).set({
      name: createdUser.user.displayName,
      avatar: createdUser.user.photoURL
    });
  };

  handleInputError = (errors, inputName) => {
    return errors.some(error => error.message.toLowerCase().includes(inputName))
      ? "error"
      : "";
  };

  render() {
    const {
      username,
      email,
      password,
      passwordConfirmation,
      errors,
      firstname,
      lastname,
      loading
    } = this.state;

    return (
      <Grid textAlign="center" verticalAlign="middle" className="app">
        <Grid.Column style={{ maxWidth: 450 }}>
        <img src={require('../../pics/logo2.png')}width="200" height="100"/>
          <Header as="h1" icon color="black" textAlign="center">
            Signup for Pingman
          </Header>
          <Form onSubmit={this.handleSubmit} size="large">
            <Segment stacked>
              <Form.Input
                fluid
                name="username"
                icon="user"
                iconPosition="left"
                placeholder="Username"
                onChange={this.handleChange}
                value={username}
                type="text"
              />

              <Form.Input
                fluid
                name="email"
                icon="mail"
                iconPosition="left"
                placeholder="Email Address"
                onChange={this.handleChange}
                value={email}
                className={this.handleInputError(errors, "email")}
                type="email"
              />
                <Form.Input
                fluid
                name="firstname"
                icon="user"
                iconPosition="left"
                placeholder="First Name"
                onChange={this.handleChange}
                value={firstname}
                type="text"
              />
                <Form.Input
                fluid
                name="lastname"
                icon="user"
                iconPosition="left"
                placeholder="Last Name"
                onChange={this.handleChange}
                value={lastname}
                type="text"
              />

              <Form.Input
                fluid
                name="password"
                icon="lock"
                iconPosition="left"
                placeholder="Password"
                onChange={this.handleChange}
                value={password}
                className={this.handleInputError(errors, "password")}
                type="password"
              />

              <Form.Input
                fluid
                name="passwordConfirmation"
                icon="repeat"
                iconPosition="left"
                placeholder="Password Confirmation"
                onChange={this.handleChange}
                value={passwordConfirmation}
                className={this.handleInputError(errors, "password")}
                type="password"
              />

              <Button
                disabled={loading}
                className={loading ? "loading" : ""}
                color="purple"
                fluid
                size="large"
              >
                Submit
              </Button>
            </Segment>
          </Form>
          {errors.length > 0 && (
            <Message error>
              <h3>Error</h3>
              {this.displayErrors(errors)}
            </Message>
          )}
          <Message>
            Already a user? <Link to="/login">Login</Link>
          </Message>
        </Grid.Column>
      </Grid>
    );
  }
}

export default Register;

/*

*/
