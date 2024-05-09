import { Alert, AlertTitle, Box, Icon, IconButton, Stack, styled, Table, TableBody, TableCell, TableHead, TablePagination, TableRow } from "@mui/material";
import { Breadcrumb } from "app/components";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

// STYLED COMPONENTS
const Container = styled("div")(({ theme }) => ({
  margin: "30px",
  [theme.breakpoints.down("sm")]: { margin: "16px" },
  "& .breadcrumb": {
    marginBottom: "30px",
    [theme.breakpoints.down("sm")]: { marginBottom: "16px" }
  }
}));

// STYLED COMPONENT
const StyledTable = styled(Table)(() => ({
  whiteSpace: "pre",
  "& thead": {
    "& tr": { "& th": { paddingLeft: 0, paddingRight: 0 } }
  },
  "& tbody": {
    "& tr": { "& td": { paddingLeft: 0, textTransform: "capitalize" } }
  }
}));

export default function Producto () {
  const url = "http://localhost:8080/api/v1/";

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [alertError, setAlertError] = useState(false);
  const [alertSuccess, setAlertSuccess] = useState(false);
  const [productos, setProductos] = useState([]);

  useEffect(() => {
    axios.get(url+"productos").then(res => {
      setProductos(res.data);
    })
  }, [])

  const handleChangePage = (_, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const deleteProduct = (productoId) => {
    axios.delete(url+"productos/"+productoId).then(res => {
      setAlertSuccess(true)
      setAlertError(false)
      axios.get(url+"productos").then(res => {
        setProductos(res.data);
      })
    }).catch((error) => {
      if(error.response.status === 500) {
        setAlertError(true)
      }
    })
  }

  return (
    <Container>
      <Stack spacing={3}>
      
      <Box className="breadcrumb">
        <Breadcrumb routeSegments={[{ name: "Listado de Productos" }]} />
      </Box>

        { alertError ? 
        <Alert hidden={alertError} severity="error">
          <AlertTitle>Error</AlertTitle>
          Ocurrio un error, no se puedo eliminar el producto.
        </Alert>
        : null }

        { alertSuccess ? 
        <Alert severity="success">
          <AlertTitle>Success</AlertTitle>
          Se ha eliminado el producto, de forma exitosa.
        </Alert>
        : null }

        <Box width="100%" overflow="auto">
          <StyledTable>
            <TableHead>
              <TableRow>
                <TableCell align="left">Código</TableCell>
                <TableCell align="center">Nombre</TableCell>
                <TableCell align="center">Caracteristicas</TableCell>
                <TableCell align="center">Precio</TableCell>
                <TableCell align="center">Empresa</TableCell>
                <TableCell align="right">Action</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
            {productos
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((subscriber, index) => (
                <TableRow key={index}>
                  <TableCell align="left">{subscriber.codigo}</TableCell>
                  <TableCell align="center">{subscriber.nombre}</TableCell>
                  <TableCell align="center">{subscriber.caracteristicas}</TableCell>
                  <TableCell align="center">{subscriber.precio}</TableCell>
                  <TableCell align="center">{subscriber.empresa}</TableCell>
                  <TableCell align="right">
                    <IconButton onClick={() => deleteProduct(subscriber.id)}>
                      <Icon color="error">delete_forever</Icon>
                    </IconButton>
                    <IconButton>
                      <Icon color="primary">edit</Icon>
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </StyledTable>
          <TablePagination
            sx={{ px: 2 }}
            page={page}
            component="div"
            rowsPerPage={rowsPerPage}
            count={productos.length}
            onPageChange={handleChangePage}
            rowsPerPageOptions={[5, 10, 25]}
            onRowsPerPageChange={handleChangeRowsPerPage}
            nextIconButtonProps={{ "aria-label": "Next Page" }}
            backIconButtonProps={{ "aria-label": "Previous Page" }}
          />
        </Box>
      </Stack>
    </Container>
  )
}