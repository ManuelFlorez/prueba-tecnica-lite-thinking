import { Box, Button, Stack, styled } from "@mui/material";
import { Breadcrumb, SimpleCard } from "app/components";
import NewEmpresaForm from "../material-kit/forms/NewEmpresaForm";
import { Link } from 'react-router-dom';

export default function NewEmpresa() {

  const Container = styled("div")(({ theme }) => ({
    margin: "30px",
    [theme.breakpoints.down("sm")]: { margin: "16px" },
    "& .breadcrumb": {
      marginBottom: "30px",
      [theme.breakpoints.down("sm")]: { marginBottom: "16px" }
    }
  }));

  return (
    <Container>
      <Stack spacing={3}>
        <Box className="breadcrumb">
          <Breadcrumb routeSegments={[{ name: "Nueva Empresas" }]} />
        </Box>

        <SimpleCard title="datos empresa">
          <NewEmpresaForm />
        </SimpleCard>

        <SimpleCard title="Opciones">
          <Button component={Link} to="/dashboard/empresa" variant="text">listado de Empresas</Button>
        </SimpleCard>
      </Stack>
    </Container>
  );
}