import React from 'react';
import PropTypes from 'prop-types';
import { withRouter, Redirect, Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Container, Alert, Button } from 'reactstrap';
import Widget from '../../components/Widget';
import { registerUser, registerError } from '../../actions/register';
import { loginUser } from '../../actions/user';
import microsoft from '../../images/microsoft.png';
import Login from '../login';

class Register extends React.Component {
    static propTypes = {
        dispatch: PropTypes.func.isRequired,
    };

    constructor(props) {
        super(props);

        this.state = {
            email: '',
            password: '',
            confirmPassword: ''
        };

        this.doRegister = this.doRegister.bind(this);
        this.googleLogin = this.googleLogin.bind(this);
        this.microsoftLogin = this.microsoftLogin.bind(this);
        this.changeEmail = this.changeEmail.bind(this);
        this.changePassword = this.changePassword.bind(this);
        this.changeConfirmPassword = this.changeConfirmPassword.bind(this);
        this.checkPassword = this.checkPassword.bind(this);
        this.isPasswordValid = this.isPasswordValid.bind(this);
    }

    changeEmail(event) {
        this.setState({email: event.target.value});
    }

    changePassword(event) {
        this.setState({password: event.target.value});
    }

    changeConfirmPassword(event) {
        this.setState({confirmPassword: event.target.value});
    }

    checkPassword() {
        if (!this.isPasswordValid()) {
            if (!this.state.password) {
                this.props.dispatch(registerError("Password field is empty"));
            } else {
                this.props.dispatch(registerError("Passwords are not equal"));
            }
            setTimeout(() => {
                this.props.dispatch(registerError());
            }, 3 * 1000)
        }
    }

    isPasswordValid() {
       return this.state.password && this.state.password === this.state.confirmPassword;
    }

    doRegister(e) {
        e.preventDefault();
        if (!this.isPasswordValid()) {
            this.checkPassword();
        } else {
            this.props.dispatch(registerUser({
                creds: {
                    email: this.state.email,
                    password: this.state.password
                },
                history: this.props.history
            }));
        }
    }

    googleLogin() {
        this.props.dispatch(loginUser({social: "google"}));
    }

    microsoftLogin() {
        this.props.dispatch(loginUser({social: "microsoft"}));
    }

    render() {
        const {from} = this.props.location.state || {from: {pathname: '/app'}}; // eslint-disable-line

        // cant access login page while logged in
        if (Login.isAuthenticated(localStorage.getItem('token'))) {
            return (
                <Redirect to={from}/>
            );
        }

        return (
            <div className="auth-page">
                <Container>
                    <h5 className="auth-logo">
                        <i className="fa fa-circle text-gray"/>
                        Sing App
                        <i className="fa fa-circle text-warning"/>
                    </h5>
                    <Widget className="widget-auth mx-auto" title={<h3 className="mt-0">Create an account</h3>}>
                        <p className="widget-auth-info">
                            Please fill all fields below
                        </p>
                        <form className="mt" onSubmit={this.doRegister}>
                            {
                                this.props.errorMessage && (
                                    <Alert className="alert-sm" color="danger">
                                        {this.props.errorMessage}
                                    </Alert>
                                )
                            }
                            <div className="form-group">
                                <input className="form-control no-border" value={this.state.email}
                                       onChange={this.changeEmail} type="text" required name="email"
                                       placeholder="Email"/>
                            </div>
                            <div className="form-group">
                                <input className="form-control no-border" value={this.state.password}
                                       onChange={this.changePassword} type="password" required name="password"
                                       placeholder="Password"/>
                            </div>
                            <div className="form-group">
                                <input className="form-control no-border" value={this.state.confirmPassword}
                                       onChange={this.changeConfirmPassword} onBlur={this.checkPassword} type="password" required name="confirmPassword"
                                       placeholder="Confirm"/>
                            </div>
                            <Button type="submit" color="inverse" className="auth-btn mb-3" size="sm">{this.props.isFetching ? 'Loading...' : 'Register'}</Button>
                            <p className="widget-auth-info">or sign up with</p>
                            <div className="social-buttons">
                                <Button onClick={this.googleLogin} color="primary" className="social-button mb-2">
                                    <i className="social-icon social-google"/>
                                    <p className="social-text">GOOGLE</p>
                                </Button>
                                <Button onClick={this.microsoftLogin} color="success" className="social-button">
                                    <i className="social-icon social-microsoft"
                                       style={{backgroundImage: `url(${microsoft})`}}/>
                                    <p className="social-text">MICROSOFT</p>
                                </Button>
                            </div>
                        </form>
                        <p className="widget-auth-info">
                            Already have the account? Login now!
                        </p>
                        <Link className="d-block text-center" to="login">Enter the account</Link>
                    </Widget>
                </Container>
                <footer className="auth-footer">
                    2019 &copy; Sing App - React Admin Dashboard Template.
                </footer>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        isFetching: state.register.isFetching,
        errorMessage: state.register.errorMessage,
    };
}

export default withRouter(connect(mapStateToProps)(Register));

