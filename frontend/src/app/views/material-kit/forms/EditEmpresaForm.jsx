import {
  Button,
  Grid,
  Icon,
  styled
} from "@mui/material";
import { Span } from "app/components/Typography";
import axios from "axios";
import { useEffect, useState } from "react";
import { TextValidator, ValidatorForm } from "react-material-ui-form-validator";
import { useNavigate } from "react-router-dom";

const TextField = styled(TextValidator)(() => ({
  width: "100%",
  marginBottom: "16px"
}));

const EditEmpresaForm = (props) => {

  const navigate = useNavigate();
  
  const url = "http://localhost:8080/api/v1/";

  const [state, setState] = useState({ date: new Date() });

  useEffect(() => {
    ValidatorForm.addValidationRule("isPasswordMatch", (value) => {
      if (value !== state.password) return false;

      return true;
    });
    return () => ValidatorForm.removeValidationRule("isPasswordMatch");
  }, [state.password]);

  const handleSubmit = (event) => {
    //console.log(event)
    //console.log(`nombre: ${nombre}`)
    //console.log(event.target.nombre.value)
    
    axios.put(url+"empresas/"+props.empresa.nit, {
      nombre: event.target.nombre.value,
      direccion: event.target.direccion.value,
      telefono: event.target.telefono.value,
    }).then(res => {
      navigate("/dashboard/empresa");
      props.handleClose(false)
    })
  };

  const handleChange = (event) => {
    event.persist();
    setState({ ...state, [event.target.name]: event.target.value });
  };

  const {
    nombre,
    direccion,
    telefono,
  } = state;

  return (
    <div>
      <ValidatorForm onSubmit={handleSubmit} onError={() => null}>
        <Grid container spacing={12}>
          <Grid item lg={12} md={6} sm={12} xs={12} sx={{ mt: 2 }}>

            <TextField
              type="text"
              name="nombre"
              label="Nombre"
              onChange={handleChange}
              value={nombre || props.empresa.nombre}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />

            <TextField
              type="text"
              name="direccion"
              label="Dirección"
              value={direccion || props.empresa.direccion}
              onChange={handleChange}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />

            <TextField
              type="text"
              name="telefono"
              label="Teléfono"
              value={telefono || props.empresa.telefono}
              onChange={handleChange}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />

          </Grid>
        </Grid>

        <Button color="primary" variant="contained" type="submit">
          <Icon>send</Icon>
          <Span sx={{ pl: 1, textTransform: "capitalize" }}>Guardar</Span>
        </Button>
      </ValidatorForm>
    </div>
  );
};

export default EditEmpresaForm;
