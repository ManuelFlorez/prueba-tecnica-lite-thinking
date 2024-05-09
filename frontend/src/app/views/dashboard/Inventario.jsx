import { Box, Button, Icon, styled } from "@mui/material";
import { Breadcrumb, SimpleCard } from "app/components";
import { Span } from "app/components/Typography";

// STYLED COMPONENTS
const Container = styled("div")(({ theme }) => ({
  margin: "30px",
  [theme.breakpoints.down("sm")]: { margin: "16px" },
  "& .breadcrumb": {
    marginBottom: "30px",
    [theme.breakpoints.down("sm")]: { marginBottom: "16px" }
  }
}));

export default function Inventario () {

  const url = "http://localhost:8080/api/v1/create-pdf";

  return (
    <Container>
      <Box className="breadcrumb">
        <Breadcrumb routeSegments={[{ name: "Invetario" }]} />
      </Box>

      <SimpleCard title="Descargar inventario en formato PDF">
        <Button color="success" variant="contained" type="submit">
          <Icon>send</Icon>
          <Span sx={{ pl: 1, textTransform: "capitalize" }}>
            <a href={url}>Crear</a>
          </Span>
        </Button>
      </SimpleCard>

    </Container>
  )
}