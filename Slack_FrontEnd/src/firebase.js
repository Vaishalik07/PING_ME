import firebase from "firebase/app";
import "firebase/auth";
import "firebase/database";
import "firebase/storage";

var config = {
  apiKey: "AIzaSyCo5EQ_ZYP9P97LM4g1WWA-lc-hrXr1YFM",
  authDomain: "slackdatabase-vaishalikoul.firebaseapp.com",
  databaseURL: "https://slackdatabase-vaishalikoul.firebaseio.com",
  projectId: "slackdatabase-vaishalikoul",
  storageBucket: "slackdatabase-vaishalikoul.appspot.com",
  messagingSenderId: "720652908964",
  appId: "1:720652908964:web:1abd0c9c76616303b6e931",
  measurementId: "G-5HLNR7CG9D"
};
firebase.initializeApp(config);

export default firebase;