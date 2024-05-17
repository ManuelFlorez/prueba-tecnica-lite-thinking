import {
  Alert,
  AlertTitle,
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Icon,
  IconButton,
  styled,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TablePagination,
  TableRow
} from "@mui/material";
import { Breadcrumb } from "app/components";
import axios from "app/utils/axios";
import { useEffect } from "react";
import { useState } from "react";
import EditEmpresaForm from "../material-kit/forms/EditEmpresaForm";

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

export default function Empresa() {
  useEffect(() => {
    axios.get("api/v1/companies").then((res) => {
      setEmpresas(res.data);
    });
  }, []);

  const handleClickOpen = async (empresa) => {
    await setEmpresaEdit(empresa);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    axios.get("api/v1/companies").then((res) => {
      setEmpresas(res.data);
    });
  };

  const [open, setOpen] = useState(false);
  const [empresaEdit, setEmpresaEdit] = useState({});
  const [empresas, setEmpresas] = useState([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const [alertError, setAlertError] = useState(false);
  const [alertSuccess, setAlertSuccess] = useState(false);

  const handleChangePage = (_, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const deleteEmpresa = (nit) => {
    axios
      .delete("api/v1/companies/" + nit)
      .then((res) => {
        setAlertSuccess(true);
        setAlertError(false);
        axios.get("api/v1/companies").then((res) => {
          setEmpresas(res.data);
        });
      })
      .catch((error) => {
        if (error.response.status === 500) {
          setAlertError(true);
        }
      });
  };

  return (
    <Container>
      <Box className="breadcrumb">
        <Breadcrumb routeSegments={[{ name: "Listado de Empresas" }]} />
      </Box>

      {alertError ? (
        <Alert hidden={alertError} severity="error">
          <AlertTitle>Error</AlertTitle>
          Si las empresas han registrado productos, no se pueden eliminar.
        </Alert>
      ) : null}

      {alertSuccess ? (
        <Alert severity="success">
          <AlertTitle>Success</AlertTitle>
          Se ha eliminado la empresa, de forma exitosa.
        </Alert>
      ) : null}

      <Box width="100%" overflow="auto">
        <StyledTable>
          <TableHead>
            <TableRow>
              <TableCell align="left">Nombre</TableCell>
              <TableCell align="center">NIT</TableCell>
              <TableCell align="center">Dirección</TableCell>
              <TableCell align="center">Teléfono</TableCell>
              <TableCell align="right">Action</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {empresas
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((subscriber, index) => (
                <TableRow key={index}>
                  <TableCell align="left">{subscriber.name}</TableCell>
                  <TableCell align="center">{subscriber.nit}</TableCell>
                  <TableCell align="center">{subscriber.address}</TableCell>
                  <TableCell align="center">{subscriber.telephone}</TableCell>
                  <TableCell align="right">
                    <IconButton onClick={() => deleteEmpresa(subscriber.nit)}>
                      <Icon color="error">delete_forever</Icon>
                    </IconButton>
                    <IconButton onClick={() => handleClickOpen(subscriber)}>
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
          count={empresas.length}
          onPageChange={handleChangePage}
          rowsPerPageOptions={[5, 10, 25]}
          onRowsPerPageChange={handleChangeRowsPerPage}
          nextIconButtonProps={{ "aria-label": "Next Page" }}
          backIconButtonProps={{ "aria-label": "Previous Page" }}
        />
      </Box>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{"Editar información de la Empresa."}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Esta es la información de la empresa que deseamos actualizar.
            <EditEmpresaForm handleClose={handleClose} empresa={empresaEdit} />
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Descartar</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
}
