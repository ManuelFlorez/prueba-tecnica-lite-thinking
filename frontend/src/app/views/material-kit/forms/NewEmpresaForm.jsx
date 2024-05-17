import { Button, Grid, Icon, styled } from "@mui/material";
import { Span } from "app/components/Typography";
import axios from "app/utils/axios";
import { useEffect, useState } from "react";
import { TextValidator, ValidatorForm } from "react-material-ui-form-validator";
import { useNavigate } from "react-router-dom";

const TextField = styled(TextValidator)(() => ({
  width: "100%",
  marginBottom: "16px"
}));

const NewEmpresaForm = () => {
  const navigate = useNavigate();

  const [state, setState] = useState({ date: new Date() });

  useEffect(() => {
    ValidatorForm.addValidationRule("isPasswordMatch", (value) => {
      if (value !== state.password) return false;

      return true;
    });
    return () => ValidatorForm.removeValidationRule("isPasswordMatch");
  }, [state.password]);

  const handleSubmit = (event) => {
    axios
      .post("api/v1/companies", {
        nit,
        nombre,
        direccion,
        telefono
      })
      .then((res) => {
        navigate("/dashboard/empresa");
      });
  };

  const handleChange = (event) => {
    event.persist();
    setState({ ...state, [event.target.name]: event.target.value });
  };

  const { nit, nombre, direccion, telefono } = state;

  return (
    <div>
      <ValidatorForm onSubmit={handleSubmit} onError={() => null}>
        <Grid container spacing={12}>
          <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
            <TextField
              type="text"
              name="nit"
              id="standard-basic"
              value={nit || ""}
              onChange={handleChange}
              errorMessages={["this field is required"]}
              label="NIT (Min length 4, Max length 9)"
              validators={["required", "minStringLength: 4", "maxStringLength: 9"]}
            />

            <TextField
              type="text"
              name="nombre"
              label="Nombre"
              onChange={handleChange}
              value={nombre || ""}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />

            <TextField
              type="text"
              name="direccion"
              label="Dirección"
              value={direccion || ""}
              onChange={handleChange}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />

            <TextField
              type="text"
              name="telefono"
              label="Teléfono"
              value={telefono || ""}
              onChange={handleChange}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />
          </Grid>
        </Grid>

        <Button color="primary" variant="contained" type="submit">
          <Icon>send</Icon>
          <Span sx={{ pl: 1, textTransform: "capitalize" }}>Crear</Span>
        </Button>
      </ValidatorForm>
    </div>
  );
};

export default NewEmpresaForm;
