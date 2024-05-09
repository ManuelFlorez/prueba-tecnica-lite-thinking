import { createContext, useEffect, useReducer } from "react";
import axios from "axios";
// CUSTOM COMPONENT
import { MatxLoading } from "app/components";

const initialState = {
  user: null,
  isInitialized: false,
  isAuthenticated: false
};

const reducer = (state, action) => {
  switch (action.type) {
    case "INIT": {
      const { isAuthenticated, user } = action.payload;
      return { ...state, isAuthenticated, isInitialized: true, user };
    }

    case "LOGIN": {
      return { ...state, isAuthenticated: true, user: action.payload.user };
    }

    case "LOGOUT": {
      return { ...state, isAuthenticated: false, user: null };
    }

    case "REGISTER": {
      const { user } = action.payload;

      return { ...state, isAuthenticated: true, user };
    }

    default:
      return state;
  }
};

const AuthContext = createContext({
  ...initialState,
  method: "JWT",
  login: () => {},
  logout: () => {},
  register: () => {}
});

export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  /*
  const login2 = async (email, password) => {
    const response = await axios.post("/api/auth/login", { email, password });
    const { user } = response.data;
    
    dispatch({ type: "LOGIN", payload: { user } });
  };
  */

  const login = async (email, password) => {
    const response = await axios.post(`http://localhost:8080/api/v1/login`, { email, password })
    console.log(response.data)
    const user = {
      id: response.data.id,
      role: response.data.roles[0],
      name: "Usuario Administrador",
      username: "jason_alexander",
      email,
      avatar: "/assets/images/face-6.png",
      age: 25
    }
    dispatch({ type: "LOGIN", payload: { user } });
  }

  const register = async (email, username, password) => {
    const response = await axios.post("/api/auth/register", { email, username, password });
    const { user } = response.data;

    dispatch({ type: "REGISTER", payload: { user } });
  };

  const logout = () => {
    dispatch({ type: "LOGOUT" });
  };

  useEffect(() => {
    (async () => {
      try {
        const { data } = await axios.get("/api/auth/profile");
        dispatch({ type: "INIT", payload: { isAuthenticated: true, user: data.user } });
      } catch (err) {
        console.error(err);
        dispatch({ type: "INIT", payload: { isAuthenticated: false, user: null } });
      }
    })();
  }, []);

  // SHOW LOADER
  if (!state.isInitialized) return <MatxLoading />;

  return (
    <AuthContext.Provider value={{ ...state, method: "JWT", login, logout, register }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
