import { Box, Button, Grid, Icon, styled } from "@mui/material";
import { Breadcrumb, SimpleCard } from "app/components";
import { Span } from "app/components/Typography";
import { useState } from "react";
import { useEffect } from "react";
import { TextValidator, ValidatorForm } from "react-material-ui-form-validator";

// STYLED COMPONENTS
const Container = styled("div")(({ theme }) => ({
  margin: "30px",
  [theme.breakpoints.down("sm")]: { margin: "16px" },
  "& .breadcrumb": {
    marginBottom: "30px",
    [theme.breakpoints.down("sm")]: { marginBottom: "16px" }
  }
}));

const TextField = styled(TextValidator)(() => ({
  width: "100%",
  marginBottom: "16px"
}));

export default function NewProducto () {

  const [state, setState] = useState({ date: new Date() });

  useEffect(() => {
    ValidatorForm.addValidationRule("isPasswordMatch", (value) => {
      if (value !== state.password) return false;

      return true;
    });
    return () => ValidatorForm.removeValidationRule("isPasswordMatch");
  }, [state.password]);

  const handleSubmit = (event) => {
    // console.log("submitted");
    // console.log(event);
  };

  const handleChange = (event) => {
    event.persist();
    setState({ ...state, [event.target.name]: event.target.value });
  };

  const {
    codigo,
    nombre,
    caracteristicas,
    precio
  } = state;

  return (
    <Container>
      <Box className="breadcrumb">
        <Breadcrumb routeSegments={[{ name: "nuevo Productos" }]} />
      </Box>

      <SimpleCard title="datos Producto">
      <ValidatorForm onSubmit={handleSubmit} onError={() => null}>
        <Grid container spacing={12}>
          <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
            <TextField
              type="text"
              name="codigo"
              id="standard-basic"
              value={codigo || ""}
              onChange={handleChange}
              errorMessages={["this field is required"]}
              label="Codigo (Min length 4, Max length 9)"
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
              value={caracteristicas || ""}
              onChange={handleChange}
              validators={["required"]}
              errorMessages={["this field is required"]}
            />

            <TextField
              type="text"
              name="telefono"
              label="Teléfono"
              value={precio || ""}
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
      </SimpleCard>
    </Container>
  )
}