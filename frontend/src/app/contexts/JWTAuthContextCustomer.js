import { MatxLoading } from "app/components";
import { useEffect, useReducer, createContext } from "react";
import axios from "app/utils/axios"; 

const initialState = {
  user: null,
  isInitialized: false,
  isAuthenticated: false
}

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

    default:
      return state;
  }
}

const AuthContext = createContext({
  ...initialState,
  method: "JWT",
  login: () => {},
  logout: () => {},
})

export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  
  const login = async (email, password) => {
    const response = await axios.post(`/login`, { email, password })
    const { user } = mapUser( response.data )

    dispatch({ type: "LOGIN", payload: { user } });
  }

  const logout = () => {
    dispatch({ type: "LOGOUT" });
  };

  const mapUser = (data) => {
    const user = {
      id: data.id,
      role: data.roles[0],
      name: data.userName === "admin@app.com" ? "Mamuel Florez" : "Externo",
      username: data.userName,
      email: data.userName,
      avatar: data.userName === "admin@app.com" ? "/assets/images/face-8.png" : "/assets/images/face-6.jpg",
      age: 25
    };
    return { user };
  }

  useEffect(() => {
    (async () =>{
      try {
        const { data } = await axios.get(`/profile`)
        dispatch({ type: "INIT", payload: { isAuthenticated: true, user: data.user } });
      } catch (err) {
        dispatch({ type: "INIT", payload: { isAuthenticated: false, user: null } });
      }
    })();
  }, []);

  if (!state.isInitialized) return <MatxLoading />;

  return (
    <AuthContext.Provider value={{ ...state, method: "JWT", login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export default AuthContext;